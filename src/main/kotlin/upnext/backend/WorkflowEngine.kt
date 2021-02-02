package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*
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
                // act on all stages
                .onEach { (stage, stageData) ->
                    // act on all automatic steps that are not yet completed
                    stage.steps
                        .map { step -> step to stageData.getStep(step) }
                        // TODO: Unit-TEST that already completed tasks are no longer executed
                        .filter { (_, stepData) -> stepData.isNotCompleted() }
                        .onEach { (step, _) ->
                            if (step.handler is WorkflowBackend.StepHandler.Automatic<S>) {
                                WorkflowStepExecutor.runBlocking(stage, step, data, step.handler)
                            }
                        }
                }
                // trigger stage transitions on all stages
                .onEach { (stage, _) -> transitionStageIfPossible(stage, data) }
        }
    }

    fun <D : Any> executeStep(subjectId: SubjectId, step: WorkflowDescription.Step<D>, stepData: D): Result<S> {

        return runFullCircle(subjectId) { data ->

            val stageAndStep = workflow.stages
                .flatMap { stage -> stage.steps.map { stage to it } }
                .firstOrNull { it.second.id == step.id }
                ?: return@runFullCircle

            if (stageAndStep.first.id !in data.activeStages) {
                // TODO: log something
                return@runFullCircle
            }

            stageAndStep
                .also { (stage, step) ->
                    if (step.handler is WorkflowBackend.StepHandler.Interactive<S, *>) {
                        WorkflowStepExecutor.runBlocking(stage, step, data, step.handler, stepData)
                    }
                }
                .also { (stage, _) -> transitionStageIfPossible(stage, data) }
        }
    }

    private fun runFullCircle(id: SubjectId, block: (data: MutableWorkflowData<S>) -> Unit): Result<S> {

        // TODO: Catch error when the subject cannot be loaded
        //       Add a comment on the workflow
        //       Mark the entire workflow as failed
        val workflowData = dataRepo.loadOrInit(id, workflow).toMutable()

        // ensure that all stages and steps are setup within the data
        // TODO: Unit-TEST for checking that all steps are initialized
        workflowData.ensureIsInitialized()

        // apply to work
        block(workflowData)

        return Result(subject = workflowData.subject, data = workflowData.persist())
    }

    private fun transitionStageIfPossible(stage: WorkflowBackend.Stage<S>, data: MutableWorkflowData<S>) {
        val applicable = stage.transitions.filter { it.handler.isApplicable(stage, data) }

        applicable
            .onEach { transition ->
                data.removeActiveStage(stage.id)
                data.addActiveStage(transition.description.target)
            }
            .onEach {
                println("Applied transitions to ${it.description.target}")
            }
    }

    private fun MutableWorkflowData<S>.ensureIsInitialized() {
        // ensure that all stages and steps are setup within the data
        workflow.stages.forEach { stage ->

            // ensure that every stage is initialized in the data
            getStage(stage).let { stageData ->

                stage.steps.forEach { step ->
                    // ensure that every step is initialized in the data
                    stageData.getStep(step).apply {
                        setStateIfUndefined(WorkflowState.Undefined)
                    }
                }
            }
        }
    }

    private fun PersistentWorkflowData<S>.toMutable(): MutableWorkflowData<S> = MutableWorkflowData(
        subject = subjectRepo.loadWorkflowSubject(subjectId)
        // TODO: use a specific exception here (CouldNotLoadWorkflowSubject)
            ?: error("Workflow subject '${subjectId.id}' was not found"),
        activeStages = activeStages.toMutableSet(),
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
            activeStages = activeStages.toSet(),
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
