package de.peekandpoke.kraft.dev.proceed

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.SerialName
import java.time.Instant
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.full.isSupertypeOf

class StepExecutor<S>(
    val step: WorkflowBackend.Step<S, *>,
    subject: S,
    val data: MutableWorkflowData<S>,
) {
    var subject = subject
        private set

    fun markProgress(current: Number, target: Number) {
        markProgress(current.toDouble() / target.toDouble())
    }

    fun markProgress(progress: Double) {
        data.setStepState(step, StepState.Progress(max(0.0, min(progress, 1.0))))
    }

    fun markAsDone() {
        data.setStepState(step, StepState.Done)
    }

    fun markAsFailed() {
        data.setStepState(step, StepState.Failed)
    }

    fun markAsSkipped() {
        data.setStepState(step, StepState.Skipped)
    }

    fun <T: Any> WorkflowData.StepValue<T>.modify(modify: (T) -> T): T = set(modify(get()))

    fun <T : Any> WorkflowData.StepValue<T>.get(): T = getValue(this)

    fun <T : Any> getValue(key: WorkflowData.StepValue<T>): T = data.getStepValue(step, key)

    fun <T : Any> WorkflowData.StepValue<T>.set(value: T): T = setValue(this, value)

    fun <T : Any> setValue(key: WorkflowData.StepValue<T>, value: T): T = value.also {
        data.setStepValue(step, key, value)
    }

    fun modifySubject(block: (S) -> S) {
        subject = block(subject)
    }
}

sealed class StepState {

    interface FinalState

    // NON-Final states ////////////////////////////////////////////

    @SerialName("open")
    object Open : StepState() {
        override fun toString() = "open"
    }

    @SerialName("retry")
    data class Retry(val count: Int) : StepState() {
        override fun toString() = "retry: $count"
    }

    @SerialName("progress")
    data class Progress(val progress: Double): StepState() {
        override fun toString() = "progress: $progress"
    }

    // FINAL states ////////////////////////////////////////////////

    @SerialName("skipped")
    object Skipped : StepState(), FinalState {
        override fun toString() = "skipped"
    }

    @SerialName("done")
    object Done : StepState(), FinalState {
        override fun toString() = "done"
    }

    @SerialName("failed")
    object Failed : StepState(), FinalState {
        override fun toString() = "failed"
    }
}

interface WorkflowData<S> {

    data class StepValue<T : Any>(val name: String, val default: T) {
        override fun toString() = name
    }

    val currentStage: StageId

    val createdAt: Instant

    val stepStates: Map<String, StepState>

    val stepData: Map<String, Map<String, Any>>

    fun isCompleted(step: WorkflowBackend.Step<S, *>) = stepStates[step.id.id] is StepState.FinalState

    fun isNotCompleted(step: WorkflowBackend.Step<S, *>) = !isCompleted(step)

    fun getStepState(step: WorkflowBackend.Step<S, *>) = stepStates[step.id.id] ?: StepState.Open

    fun <T : Any> getStepValue(step: WorkflowBackend.Step<S, *>, key: StepValue<T>): T {

        val map = stepData[step.id.id] ?: return key.default

        return when {
            map.containsKey(key.name) -> map[key.name]!! as? T ?: key.default
            else -> key.default
        }
    }
}

data class PersistentWorkflowData<S>(
    override val currentStage: StageId,
    override val createdAt: Instant = Instant.now(),
    override val stepStates: Map<String, StepState> = emptyMap(),
    override val stepData: Map<String, Map<String, Any>> = emptyMap(),
) : WorkflowData<S> {

    fun toMutable() = MutableWorkflowData<S>(
        currentStage = currentStage,
        createdAt = createdAt,
        stepStates = stepStates.toMutableMap(),
        stepData = stepData.entries.map { it.key to it.value.toMutableMap() }.toMap().toMutableMap()
    )
}

class MutableWorkflowData<S>(
    override val currentStage: StageId,
    override val createdAt: Instant,
    override val stepStates: MutableMap<String, StepState>,
    override val stepData: MutableMap<String, MutableMap<String, Any>>,
) : WorkflowData<S> {

    fun toPersistent() = PersistentWorkflowData<S>(
        currentStage = currentStage,
        createdAt = createdAt,
        stepStates = stepStates.toMap(),
        stepData = stepData.entries.map { it.key to it.value.toMap() }.toMap(),
    )

    fun setStepState(step: WorkflowBackend.Step<S, *>, state: StepState) {
        stepStates[step.id.id] = state
    }

    fun <T : Any> setStepValue(step: WorkflowBackend.Step<S, *>, key: WorkflowData.StepValue<T>, value: T) {
        val map = stepData.getOrPut(step.id.id) { mutableMapOf() }

        map[key.name] = value
    }
}

class WorkflowEngine<S>(
    val workflow: WorkflowBackend<S>,
    subject: S,
    data: PersistentWorkflowData<S>
) {
    val initial: S = subject

    var result: S = subject
        private set

    val workflowData = data.toMutable()

    init {
        // TODO: we need to check the consistency of the workflow upfront
        //       1. are GIDs unique?
        //       2. are all Actions present that are referenced in the StepHandlers?
    }

    fun isStageCompleted(stageId: StageId): Boolean {
        return getIncompleteSteps(stageId).isEmpty()
    }

    fun getIncompleteSteps(stageId: StageId): List<WorkflowBackend.Step<S, *>> {

        return when (val stage = workflow.stages.firstOrNull { it.id == stageId }) {

            null -> emptyList()

            else -> stage.steps.filter { workflowData.isNotCompleted(it) }
        }
    }

    fun runAutomatic() {

        val currentStage = workflow.stages.firstOrNull { it.id == workflowData.currentStage }
        // TODO: Really raise an exception here... or just ignore... or log something?
            ?: error("Stage '${workflow.entry.id}' not present")

        currentStage.steps
            // TODO: TEST that already completed tasks are no longer executed
            .filter { !workflowData.isCompleted(it) }
            .forEach { step ->

                // NOTICE: all steps in a single stage can be executed out of order

                when (val handler = step.handler) {

                    is WorkflowBackend.StepHandler.Automatic<S> -> {

                        runBlocking {

                            StepExecutor(step = step, subject = result, data = workflowData).also { executor ->
                                handler.apply { executor.execute() }

                                result = executor.subject
                            }
                        }
                    }

                    else -> { /* do nothing */
                    }
                }
            }
    }

    fun executeStep(stepGid: StepId<*>, stepData: Any) {

        val step = workflow.stages.flatMap { it.steps }.firstOrNull { it.id == stepGid }

        step?.let { it ->

            when (val handler = it.handler) {
                is WorkflowBackend.StepHandler.Interactive<S, *> -> {

                    // get the 'execute' method of the interactive handler
                    val executeMethod = handler::class.declaredMemberExtensionFunctions.first { it.name == "execute" }
                    // get the type of the stepData
                    val stepDataType = stepData::class.createType()
                    // 0. param: Instance parameter
                    // 1. param: Receiver parameter
                    // 2. param: The data param of the execute method.
                    val dataParam = executeMethod.parameters[2]

                    // Check if the given data can be used to call the execute method
                    if (dataParam.type.isSupertypeOf(stepDataType)) {

                        runBlocking {

                            StepExecutor(step = step, subject = result, data = workflowData).also { executor ->
                                @Suppress("UNCHECKED_CAST")
                                (handler as WorkflowBackend.StepHandler.Interactive<S, Any>).apply {
                                    executor.execute(stepData)
                                }

                                result = executor.subject
                            }
                        }
                    } else {
                        // TODO: log some message
                        println("ERROR: incompatible data of type '$stepDataType'")
                    }

                }

                else -> { /* do nothing */
                }
            }
        }
    }
}
