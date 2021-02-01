package de.peekandpoke.kraft.dev.proceed.shared

data class WorkflowId(val id: String)

data class StageId(val id: String) {
    fun stepId(stepId: String) = StepId("$id::$stepId")
}

data class StepId(val id: String)

