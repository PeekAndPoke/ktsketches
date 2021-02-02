package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import kotlinx.coroutines.runBlocking
import java.time.Instant
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.full.isSupertypeOf

class WorkflowEngine<S>(
    val workflow: WorkflowBackend<S>,
    val dataRepo: WorkflowDataRepository<S>,
    val subjectRepo: WorkflowSubjectRepository<S>,
) {
    data class Result<S>(
        val subject: S,
        val data: PersistentWorkflowData<S>,
    )

//    fun isStageCompleted(stageId: WorkflowStageId): Boolean {
//        return getIncompleteSteps(stageId).isEmpty()
//    }
//
//    fun getIncompleteSteps(stageId: WorkflowStageId): List<WorkflowData.StepData<S>> {
//
//        return when (val stage = workflow.stages.firstOrNull { it.id == stageId }) {
//
//            null -> emptyList()
//
//            else -> stage.steps
//                .map { workflowData.getStage(stageId).getStep(it.id) }
//                .filter { it.isNotCompleted() }
//        }
//    }

    fun runAutomatic(subjectId: SubjectId): Result<S> {

        // TODO: catch error when subject cannot be loaded
        val workflowData = dataRepo.loadOrInit(subjectId, workflow).toMutable()

        val stages = workflow.stages.filter { it.id in workflowData.activeStages }

        stages
            // TODO: Unit-TEST that already completed tasks are no longer executed
            .map { stage -> stage to workflowData.getStage(stage) }
            .forEach { (stage, stageData) ->

                stage.steps
                    .map { step -> step to stageData.getStep(step) }
                    .filter { (_, stepData) -> stepData.isNotCompleted() }
                    .onEach { (step, _) ->
                        // IMPORTANT: When a stage is entered we need to ensure that every step gets a state!
                        workflowData.getStage(stage).getStep(step).setStateIfUndefined(WorkflowState.Open)
                    }
                    .onEach { (step, _) ->

                        when (val handler = step.handler) {

                            is WorkflowBackend.StepHandler.Automatic<S> -> {

                                runBlocking {

                                    WorkflowStepExecutor(
                                        stage = stage,
                                        step = step,
                                        workflowData = workflowData
                                    ).also { executor ->
                                        handler.apply { executor.execute() }
                                    }
                                }
                            }
                        }
                    }
            }

        // TODO: unify this with 'executeStep'
        return Result(
            subject = workflowData.subject,
            data = workflowData.persist()
        )
    }

    fun <D : Any> executeStep(subjectId: SubjectId, step: WorkflowDescription.Step<D>, stepData: D): Result<S> {

        val workflowData = dataRepo.loadOrInit(subjectId, workflow).toMutable()

        val found = workflow.stages
            .flatMap { stage -> stage.steps.map { stage to it } }
            .firstOrNull { it.second.id == step.id }

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
                                workflowData = workflowData
                            ).also { executor ->
                                @Suppress("UNCHECKED_CAST")
                                (handler as WorkflowBackend.StepHandler.Interactive<S, Any>).apply {
                                    executor.execute(stepData)
                                }
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

        // TODO: unify this with 'runAutomatic'
        return Result(
            subject = workflowData.subject,
            data = workflowData.persist()
        )
    }

    private fun PersistentWorkflowData<S>.toMutable(): MutableWorkflowData<S> = MutableWorkflowData(
        subject = subjectRepo.loadWorkflowSubject(subjectId)
        // TODO: use a specific exception here (CouldNotLoadWorkflowSubject)
            ?: error("Workflow subject '${subjectId.id}' was not found"),
        activeStages = activeStages.toMutableList(),
        createdAt = Instant.ofEpochMilli(createdAt.timestamp),
        stages = stages.mapValues { (_, stage) ->
            MutableWorkflowData.MutableStageData(
                id = stage.id,
                steps = stage.steps.mapValues { (_, step) ->
                    MutableWorkflowData.MutableStepData<S>(
                        id = step.id,
                        state = step.state,
                        data = step.data.toMutableMap(),
                        logs = step.logs.toMutableList(),
                    )
                }.toMutableMap()
            )
        }.toMutableMap()
    )

    private fun MutableWorkflowData<S>.persist(): PersistentWorkflowData<S> {

        // Save the subject in case it has changed
        val savedSubjectId = subjectRepo.saveWorkflowSubjectIfChanged(initialSubject, subject)

        // Create persistent workflow data
        val persistent = PersistentWorkflowData<S>(
            subjectId = savedSubjectId,
            activeStages = activeStages.toList(),
            createdAt = PortableDateTime(createdAt.toEpochMilli()),
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

        // Save the persistent workflow data
        dataRepo.save(persistent)

        return persistent
    }
}
