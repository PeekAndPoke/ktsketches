package de.peekandpoke.ktorfx.upnext.shared

interface WorkflowDescription<S> {
    val id: WorkflowId
    val stages: List<Stage>
    val entryPoints: List<StageId>

    interface Stage {
        val id: StageId
        val steps: List<Step<*>>
    }

    interface Step<D> {
        val id: StepId
    }

    abstract class SimpleStage(id: String) : Stage {

        final override val id: StageId = StageId(id)

        final override val steps: List<Step<*>> get() = _steps

        private val _steps: MutableList<Step<*>> = mutableListOf()

        @JvmName("step_Unit")
        fun step(stepId: String): Step<Unit> = step<Unit>(stepId)

        fun <D> step(stepId: String): Step<D> = SimpleStep<D>(
            id = id.stepId(stepId)
        ).also { _steps.add(it) }
    }

    data class SimpleStep<D>(
        override val id: StepId
    ) : Step<D>
}
