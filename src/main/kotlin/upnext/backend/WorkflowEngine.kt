package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.PersistentWorkflowData
import de.peekandpoke.ktorfx.upnext.shared.SubjectId
import de.peekandpoke.ktorfx.upnext.shared.WorkflowDescription
import de.peekandpoke.ktorfx.upnext.shared.WorkflowState
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import java.time.Instant

class WorkflowEngine<S>(
    val workflow: WorkflowBackend<S>,
    val dataRepo: WorkflowDataRepository<S>,
    val subjectRepo: WorkflowSubjectRepository<S>,
) {
    data class Result<S>(
        val subject: S,
        val data: PersistentWorkflowData<S>,
    )

    fun runAutomatic(subjectId: SubjectId): Result<S> {

        return runFullCircle(subjectId) { data ->

            val stages = workflow.stages.filter { it.id in data.activeStages }

            stages
                .map { stage -> stage to data.getStage(stage) }
                // TODO: Unit-TEST for checking that all steps are initialized
                .onEach { (stage, _) -> stage.ensureStepsAreInitialized(data) }
                .forEach { (stage, stageData) ->

                    stage.steps
                        .map { step -> step to stageData.getStep(step) }
                        // TODO: Unit-TEST that already completed tasks are no longer executed
                        .filter { (_, stepData) -> stepData.isNotCompleted() }
                        .onEach { (step, _) ->

                            when (val handler = step.handler) {
                                is WorkflowBackend.StepHandler.Automatic<S> -> {
                                    WorkflowStepExecutor.runBlocking(stage, step, data, handler)
                                }
                            }
                        }
                }
        }
    }

    fun <D : Any> executeStep(subjectId: SubjectId, step: WorkflowDescription.Step<D>, stepData: D): Result<S> {

        return runFullCircle(subjectId) { workflowData ->

            val found = workflow.stages
                .flatMap { stage -> stage.steps.map { stage to it } }
                .firstOrNull { it.second.id == step.id }

            found
                // TODO: Unit-TEST for checking that all steps are initialized
                ?.also { (stage, _) -> stage.ensureStepsAreInitialized(workflowData) }
                ?.let { (stage, step) ->

                    when (val handler = step.handler) {
                        is WorkflowBackend.StepHandler.Interactive<S, *> -> {
                            WorkflowStepExecutor.runBlocking(stage, step, workflowData, handler, stepData)
                        }

                        else -> { /* do nothing */
                        }
                    }
                }
        }
    }

    private fun runFullCircle(id: SubjectId, block: (data: MutableWorkflowData<S>) -> Unit): Result<S> {

        // TODO: Catch error when the subject cannot be loaded
        //       Add a comment on the workflow
        //       Mark the entire workflow as failed
        val workflowData = dataRepo.loadOrInit(id, workflow).toMutable()

        block(workflowData)

        return Result(
            subject = workflowData.subject,
            data = workflowData.persist()
        )
    }

    private fun WorkflowBackend.Stage<S>.ensureStepsAreInitialized(workflowData: MutableWorkflowData<S>) {
        steps.forEach { step ->
            // IMPORTANT: When a stage is entered we need to ensure that every step gets a state!
            workflowData.getStage(this).getStep(step).setStateIfUndefined(WorkflowState.Open)
        }
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
