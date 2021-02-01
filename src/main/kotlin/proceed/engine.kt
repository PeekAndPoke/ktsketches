package de.peekandpoke.kraft.dev.proceed

import de.peekandpoke.kraft.dev.proceed.shared.*
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import kotlinx.coroutines.runBlocking
import java.time.Instant
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.full.isSupertypeOf

class StepExecutor<S>(
    val stage: WorkflowBackend.Stage<S>,
    val step: WorkflowBackend.Step<S, *>,
    subject: S,
    val workflowData: MutableWorkflowData<S>,
) {
    var subject: S = subject
        private set

    val stageData: MutableWorkflowData.MutableStageData<S>
        get() = workflowData.getStage(stage)

    val stepData: MutableWorkflowData.MutableStepData<S>
        get() = stageData.getStep(step)

    fun setState(state: WorkflowState) {
        stepData.apply {
            setState(state)
            addLog(logEntry(state = state))
        }
    }

    fun markAsOpen(): Unit = setState(WorkflowState.Open)

    fun markProgress(current: Number, target: Number): Unit =
        markProgress(current.toDouble() / target.toDouble())

    fun markProgress(progress: Double): Unit = setState(WorkflowState.Progress(max(0.0, min(progress, 1.0))))

    fun markAsDone(): Unit = setState(WorkflowState.Done)

    fun markAsSkipped(): Unit = setState(WorkflowState.Skipped)

    fun markAsFailed(): Unit = setState(WorkflowState.Failed)

    fun <T : Any> WorkflowData.Value<T>.modify(modify: (T) -> T): T = set(modify(get()))

    fun <T : Any> WorkflowData.Value<T>.get(): T = getValue(this)

    fun <T : Any> getValue(key: WorkflowData.Value<T>): T = stepData.getValue(step, key)

    fun <T : Any> WorkflowData.Value<T>.set(value: T): T = setValue(this, value)

    fun <T : Any> setValue(key: WorkflowData.Value<T>, value: T): T = value.also {
        stepData.apply {
            setValue(key, value)
            addLog(logEntry(key = key, value = value))
        }
    }

    fun modifySubject(block: (S) -> S) {
        subject = block(subject)
    }

    /**
     * Adds a log to the step
     */
    fun comment(comment: String) {
        stepData.addLog(
            WorkflowData.LogEntry.Comment(ts = now(), comment = comment)
        )
    }

    private fun logEntry(state: WorkflowState) =
        WorkflowData.LogEntry.StateChange(ts = now(), state = state)

    private fun <T : Any> logEntry(key: WorkflowData.Value<T>, value: T) =
        WorkflowData.LogEntry.ValueChange(ts = now(), key = key.name, value = value.toString())

    private fun now() = PortableDateTime(Instant.now().toEpochMilli())
}


class MutableWorkflowData<S>(
    override val currentStage: StageId,
    override val createdAt: Instant,
    override val stages: MutableMap<String, MutableStageData<S>>,
) : WorkflowData<S> {

    companion object {
        fun <S> of(src: WorkflowData<S>): MutableWorkflowData<S> = MutableWorkflowData(
            currentStage = src.currentStage,
            createdAt = src.createdAt,
            stages = src.stages.mapValues { (_, stage) ->
                MutableStageData(
                    id = stage.id,
                    steps = stage.steps.mapValues { (_, step) ->
                        MutableStepData<S>(
                            id = step.id,
                            state = step.state,
                            data = step.data.toMutableMap(),
                            logs = step.logs.toMutableList(),
                        )
                    }.toMutableMap()
                )
            }.toMutableMap()
        )
    }

    class MutableStageData<S>(
        override val id: StageId,
        override val steps: MutableMap<String, MutableStepData<S>>
    ) : WorkflowData.StageData<S> {

        fun getStep(stepId: StepId): MutableStepData<S> = steps.getOrPut(stepId.id) {
            MutableStepData(
                id = stepId,
                state = WorkflowState.Undefined,
                data = mutableMapOf(),
                logs = mutableListOf(),
            )
        }

        override fun getStep(step: WorkflowBackend.Step<S, *>): MutableStepData<S> = getStep(step.id)
    }

    class MutableStepData<S>(
        override val id: StepId,
        state: WorkflowState,
        override val data: MutableMap<String, Any>,
        override val logs: MutableList<WorkflowData.LogEntry>,
    ) : WorkflowData.StepData<S> {

        override var state: WorkflowState = state
            private set

        fun setState(state: WorkflowState) {
            this.state = state
        }

        fun <T : Any> setValue(key: WorkflowData.Value<T>, value: T) {
            data[key.name] = value
        }

        fun addLog(entry: WorkflowData.LogEntry) {
            logs.add(entry)
        }
    }

    fun getStage(stageId: StageId): MutableStageData<S> = stages.getOrPut(stageId.id) {
        MutableStageData(
            id = stageId,
            steps = mutableMapOf()
        )
    }

    override fun getStage(stage: WorkflowBackend.Stage<S>): MutableStageData<S> = getStage(stage.id)

    fun toPersistent(): PersistentWorkflowData<S> = PersistentWorkflowData(
        currentStage = currentStage,
        createdAt = createdAt,
        stages = stages.mapValues { (_, stage) ->
            PersistentWorkflowData.PersistentStageData(
                id = stage.id,
                steps = stage.steps.mapValues { (_, step) ->
                    PersistentWorkflowData.PersistentStepData(
                        id = step.id,
                        state = step.state,
                        data = step.data.toMap(),
                        logs = step.logs.toList(),
                    )
                }
            )
        },
    )
}

class WorkflowEngine<S>(
    val workflow: WorkflowBackend<S>,
    subject: S,
    data: PersistentWorkflowData<S>
) {
    val initial: S = subject

    var result: S = subject
        private set

    val workflowData = MutableWorkflowData.of(data)

    init {
        // TODO: we need to check the consistency of the workflow upfront
        //       1. are GIDs unique?
        //       2. are all Actions present that are referenced in the StepHandlers?
    }

    fun isStageCompleted(stageId: StageId): Boolean {
        return getIncompleteSteps(stageId).isEmpty()
    }

    fun getIncompleteSteps(stageId: StageId): List<WorkflowData.StepData<S>> {

        return when (val stage = workflow.stages.firstOrNull { it.id == stageId }) {

            null -> emptyList()

            else -> stage.steps
                .map { workflowData.getStage(stageId).getStep(it.id) }
                .filter { it.isNotCompleted() }
        }
    }

    fun runAutomatic() {

        val currentStage = workflow.stages.firstOrNull { it.id == workflowData.currentStage }
        // TODO: Really raise an exception here... or just ignore... or log something?
            ?: error("Stage '${workflow.entry.id}' not present")

        currentStage.steps
            // TODO: Unit-TEST that already completed tasks are no longer executed
            .filter { !workflowData.getStage(currentStage).getStep(it.id).isCompleted() }
            .forEach { step ->

                // NOTICE: all steps in a single stage can be executed out of order

                when (val handler = step.handler) {

                    is WorkflowBackend.StepHandler.Automatic<S> -> {

                        runBlocking {

                            StepExecutor(
                                stage = currentStage,
                                step = step,
                                subject = result,
                                workflowData = workflowData
                            ).also { executor ->
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

    fun <D : Any> executeStep(step: WorkflowDescription.Step<D>, stepData: D) {

        val found = workflow.stages
            .flatMap { stage -> stage.steps.map { stage to it } }
            .firstOrNull { it.second.description.id == step.id }

        found?.let { (stage, step) ->

            when (val handler = step.handler) {
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

                            StepExecutor(
                                stage = stage,
                                step = step,
                                subject = result,
                                workflowData = workflowData
                            ).also { executor ->
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
