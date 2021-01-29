package de.peekandpoke.kraft.dev.proceed

import kotlinx.coroutines.runBlocking
import java.time.Instant
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.full.isSupertypeOf

class StepExecutor<S>(
    val step: WorkflowBackend.Step<S, *>,
    subject: S,
    data: WorkflowData<S>,
) {
    var data: WorkflowData<S> = data
        private set

    var subject = subject
        private set

    fun markAsDone() {
        data = data.withTaskState(step, StepState.Done)
    }

    fun markAsFailed() {
        data = data.withTaskState(step, StepState.Failed)
    }

    fun markAsSkipped() {
        data = data.withTaskState(step, StepState.Skipped)
    }

    fun modifySubject(block: (S) -> S) {
        subject = block(subject)
    }
}

sealed class StepState {

    interface FinalState

    // NON-Final states ////////////////////////////////////////////

    object Open : StepState()

    class Retry(val count: Int) : StepState()

    // FINAL states ////////////////////////////////////////////////

    object Skipped : StepState(), FinalState

    object Done : StepState(), FinalState

    object Failed : StepState(), FinalState
}

data class WorkflowData<S>(
    val currentStage: StageId,
    val createdAt: Instant = Instant.now(),
    val stepStates: Map<WorkflowBackend.Step<S, *>, StepState> = emptyMap()
) {
    fun stepState(step: WorkflowBackend.Step<S, *>) = stepStates[step] ?: StepState.Open

    fun withTaskState(step: WorkflowBackend.Step<S, *>, state: StepState) = copy(
        stepStates = stepStates.plus(step to state)
    )

    fun isCompleted(step: WorkflowBackend.Step<S, *>) = stepStates[step] is StepState.FinalState

    fun isNotCompleted(step: WorkflowBackend.Step<S, *>) = !isCompleted(step)
}


class WorkflowEngine<S>(
    val workflow: WorkflowBackend<S>,
    subject: S,
    data: WorkflowData<S>
) {
    val initial: S = subject

    var result: S = subject
        private set

    var workflowData = data
        private set

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

                                workflowData = executor.data
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

                                workflowData = executor.data
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
