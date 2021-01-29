package de.peekandpoke.kraft.dev.proceed


fun <S, B : S> createWorkflowBackend(
    workflow: Workflow<S>,
    block: WorkflowBackendBuilder<S, B>.() -> Unit
): WorkflowBackend<B> =
    WorkflowBackendBuilder<S, B>(workflow).apply(block).build()


class WorkflowBackendBuilder<S, B : S>(private val workflow: Workflow<S>) {

    private val stepsMap = mutableMapOf<StepId<*>, WorkflowBackend.Step<B, *>>()

    infix fun <D> StepId<D>.handledBy(handler: WorkflowBackend.StepHandler<B, D>) {

        stepsMap[this] = WorkflowBackend.Step(
            id = this,
            handler = handler
            // TODO: how about the visibility
        )
    }

    fun build(): WorkflowBackend<B> {
        val missingHandlers = workflow.stages
            .flatMap { it.steps }
            .filter { !stepsMap.contains(it) }

        if (missingHandlers.isNotEmpty()) {
            error("The following steps do not have a handler assigned: ${missingHandlers.map { "'${it.id}'" }}")
        }

        return WorkflowBackend<B>(
            id = workflow.id,
            entry = workflow.entry,
            stages = workflow.stages.map { stage ->
                WorkflowBackend.Stage<B>(
                    id = stage.id,
                    steps = stage.steps.map { step ->
                        stepsMap[step]!!
                    }
                )
            }
        )
    }
}

class WorkflowBackend<S>(
    val id: WorkflowId,
    val entry: StageId,
    val stages: List<Stage<S>>,
) {
    class Stage<S>(
        val id: StageId,
        val steps: List<Step<S, *>>,
    )

    class Step<S, D>(
        val id: StepId<D>,
        val handler: StepHandler<S, D>,
        val visibility: (S) -> Boolean = { true },
    )

    sealed class StepHandler<S, D> {

//    abstract val id: StepId<D>

        abstract class Automatic<S> : StepHandler<S, Unit>() {
            abstract suspend fun StepExecutor<S>.execute()
        }

        abstract class Interactive<S, D> : StepHandler<S, D>() {
            abstract suspend fun StepExecutor<S>.execute(data: D)
        }
    }
}


