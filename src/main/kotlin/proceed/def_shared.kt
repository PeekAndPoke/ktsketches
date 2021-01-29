package de.peekandpoke.kraft.dev.proceed

data class WorkflowId(val id: String)

data class StageId(val id: String) {
    fun <D> stepId(stepId: String) = StepId<D>("$id::$stepId")
}

data class StepId<D>(val id: String)

interface Workflow<S> {
    val id: WorkflowId
    val stages: List<Stage>
    val entry: StageId

    interface Stage {
        val id: StageId
        val steps: List<StepId<*>>
    }

    abstract class SimpleStage(id: String) : Stage {

        final override val id: StageId = StageId(id)

        final override val steps: List<StepId<*>> get() = _steps

        private val _steps: MutableList<StepId<*>> = mutableListOf()

        @JvmName("step_Unit")
        fun step(stepId: String): StepId<Unit> = step<Unit>(stepId)

        fun <D> step(stepId: String): StepId<D> = id.stepId<D>(stepId).also {
            _steps.add(it)
        }
    }
}
