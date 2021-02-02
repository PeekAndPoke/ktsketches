package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*

class WorkflowBackend<S>(
    val id: WorkflowId,
    val entryPoints: List<WorkflowStageId>,
    val stages: List<Stage<S>>,
) {
    class Stage<S>(
        val id: WorkflowStageId,
        val steps: List<Step<S, *>>,
    ) {
        val idStr get() = id.id
    }

    class Step<S, D>(
        val description: WorkflowDescription.Step<D>,
        val handler: StepHandler<S, D>,
        val visibility: (S) -> Boolean = { true },
    ) {
        val id get() = description.id
        val idStr get() = id.id
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

}
