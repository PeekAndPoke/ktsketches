package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.WorkflowDescription

class WorkflowBackendBuilder<S, B : S>(private val workflow: WorkflowDescription<S>) {

    private val stepsMap = mutableMapOf<WorkflowDescription.Step<*>, WorkflowBackend.Step<B, *>>()

    infix fun <D> WorkflowDescription.Step<D>.handledBy(handler: WorkflowBackend.StepHandler<B, D>) {

        stepsMap[this] = WorkflowBackend.Step(
            description = this,
            handler = handler
            // TODO: how about the visibility
        )
    }

    fun build(): WorkflowBackend<B> {

        val errors = listOf(
            *checkForUniqueStageIds(),
            *checkForUniqueStepIds(),
            *checkStageEntryPointsAreNotEmpty(),
            *checkStageEntryPointsExist(),
            *checkAllStepsHaveAHandler(),
        )

        if (errors.isNotEmpty()) {
            // TODO: use specific InvalidWorkflowDescription exception for this
            error("The workflow '${workflow.id}' has errors: \n${errors.joinToString("\n")}}")
        }

        return WorkflowBackend(
            id = workflow.id,
            entryPoints = workflow.entryPoints,
            stages = workflow.stages.map { stage ->
                WorkflowBackend.Stage(
                    id = stage.id,
                    steps = stage.steps.map { step ->
                        stepsMap[step]!!
                    }
                )
            }
        )
    }

    private fun checkForUniqueStageIds(): Array<out String> {
        return workflow.stages
            .groupBy { it.id }
            .filter { (_, v) -> v.size > 1 }
            .map { (k, _) -> "Id of stage '${k.id}' is not unique" }
            .toTypedArray()
    }

    private fun checkForUniqueStepIds(): Array<out String> {
        return workflow.stages
            .flatMap { it.steps }
            .groupBy { it.id }
            .filter { (_, v) -> v.size > 1 }
            .map { (k, _) -> "Id of step '${k.id}' is not unique" }
            .toTypedArray()
    }

    private fun checkStageEntryPointsAreNotEmpty(): Array<out String> {
        return when(workflow.entryPoints.isEmpty()) {
            true -> arrayOf("There are no stage entry points defined")
            else -> arrayOf()
        }
    }

    private fun checkStageEntryPointsExist(): Array<out String> {
        val allStageIds = workflow.stages.map { it.id }

        return workflow.entryPoints
            .filter { it !in allStageIds }
            .map { "Stage entry point '${it.id}' does not exist"}
            .toTypedArray()
    }

    private fun checkAllStepsHaveAHandler(): Array<out String> {
        val missingHandlers = workflow.stages
            .flatMap { it.steps }
            .filter { !stepsMap.contains(it) }

        return missingHandlers
            .map { "Step '${it.id}' does not have a backend handler" }
            .toTypedArray()
    }
}
