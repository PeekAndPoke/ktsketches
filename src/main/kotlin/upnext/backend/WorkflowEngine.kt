package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.PersistentWorkflowData
import de.peekandpoke.ktorfx.upnext.shared.StageId
import de.peekandpoke.ktorfx.upnext.shared.WorkflowData
import de.peekandpoke.ktorfx.upnext.shared.WorkflowDescription
import kotlinx.coroutines.runBlocking
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.full.isSupertypeOf

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

        val stages = workflow.stages.filter { it.id in workflowData.activeStages }

        stages
            // TODO: Unit-TEST that already completed tasks are no longer executed
            .map { stage -> stage to workflowData.getStage(stage) }
            .forEach { (stage, stageData) ->

                stage.steps
                    .map { step -> step to stageData.getStep(step) }
                    .filter { (_, stepData) -> stepData.isNotCompleted() }
                    .forEach { (step, _) ->

                        when (val handler = step.handler) {

                            is WorkflowBackend.StepHandler.Automatic<S> -> {

                                runBlocking {

                                    WorkflowStepExecutor(
                                        stage = stage,
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

                            WorkflowStepExecutor(
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
