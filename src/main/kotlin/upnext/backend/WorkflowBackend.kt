package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*

class WorkflowBackend<S>(
    val id: WorkflowId,
    val entryPoints: Set<StageId>,
    val stages: List<Stage<S>>,
) {
    class Stage<S>(
        val description: WorkflowDescription.Stage,
        val steps: List<Step<S, *>>,
        val transitions: List<StageTransition<S>>,
    ) {
        val id: StageId get() = description.id
    }

    class StageTransition<S>(
        val description: WorkflowDescription.StageTransition,
        val handler: TransitionHandler<S>
    ) {
        val id: StageTransitionId get() = description.id
    }

    class Step<S, D>(
        val description: WorkflowDescription.Step<D>,
        val handler: StepHandler<S, D>,
    ) {
        val id: StepId get() = description.id
    }

    sealed class StepHandler<S, D> {

//    abstract val id: StepId<D>

        abstract class Automatic<S> : StepHandler<S, Unit>() {
            abstract suspend fun WorkflowStepExecutor<S>.execute()
        }

        abstract class Interactive<S, D> : StepHandler<S, D>() {
            abstract suspend fun WorkflowStepExecutor<S>.execute(data: D)
        }
    }

    interface TransitionHandler<S> {
        fun isApplicable(stage: Stage<S>, workflowData: MutableWorkflowData<S>): Boolean

        class WhenAllStepsAreCompleted<S> : TransitionHandler<S> {
            override fun isApplicable(stage: Stage<S>, workflowData: MutableWorkflowData<S>): Boolean {
                return stage.steps.all { step ->
                    workflowData.getStage(stage).getStep(step).isCompleted()
                }
            }
        }
    }
}
