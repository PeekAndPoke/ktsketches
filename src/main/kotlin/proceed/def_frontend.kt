package de.peekandpoke.kraft.dev.proceed

data class WorkflowModel(
    val gid: WorkflowId,
    val stages: List<StageModel>,
)

data class StageModel(
    val gid: StageId,
    val steps: List<StepModel<*>>,
)

data class StepModel<D>(
    val gid: StepId<D>,
)
