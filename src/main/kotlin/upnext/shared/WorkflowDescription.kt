package de.peekandpoke.ktorfx.upnext.shared

interface WorkflowDescription<S> {
    val id: WorkflowId
    val stages: List<Stage>
    val entryPoints: Set<StageId>

    interface Stage {
        val id: StageId
        val steps: List<Step<*>>
        val transitions: List<StageTransition>
    }

    interface StageTransition {
        val id: StageTransitionId
        val targets: Set<StageId>
    }

    interface Step<D> {
        val id: StepId
    }

    abstract class SimpleStage(id: String) : Stage {

        final override val id: StageId = StageId(id)

        final override val steps: List<Step<*>> get() = _steps

        final override val transitions: List<StageTransition> get() = _transitions

        private val _steps: MutableList<Step<*>> = mutableListOf()

        private val _transitions: MutableList<StageTransition> = mutableListOf()

        @JvmName("step_Unit")
        fun step(stepId: String): Step<Unit> = step<Unit>(stepId)

        fun <D> step(stepId: String): Step<D> {
            return SimpleStep<D>(id = id.stepId(stepId)).also { _steps.add(it) }
        }

        fun transition(transitionId: String, provider: () -> Set<Stage>): StageTransition {
            return SimpleStageTransition(id = id.transitionId(transitionId)) { provider().map { it.id }.toSet() }
                .also { _transitions.add(it) }
        }
    }

    data class SimpleStep<D>(
        override val id: StepId
    ) : Step<D>

    data class SimpleStageTransition(
        override val id: StageTransitionId,
        val provider: () -> Set<StageId>,
    ) : StageTransition {
        override val targets: Set<StageId>
            get() = provider()
    }
}
