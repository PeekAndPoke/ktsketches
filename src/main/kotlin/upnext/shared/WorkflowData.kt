package de.peekandpoke.ktorfx.upnext.shared

import de.peekandpoke.ktorfx.upnext.backend.WorkflowBackend
import upnext.shared.WorkflowLogEntry

interface WorkflowData<S> {

    data class Value<T : Any>(val name: String, val default: T) {
        override fun toString() = name
    }

    interface StageData<S> {
        val id: StageId
        val steps: Map<String, StepData<S>>

        fun getStep(step: WorkflowBackend.Step<S, *>): StepData<S>? = steps[step.id.id]
    }

    interface StepData<S> {
        val id: StepId
        val state: StepState
        val data: Map<String, Any>
        val logs: List<WorkflowLogEntry>

        fun isCompleted(): Boolean = state is StepState.FinalState

        fun isNotCompleted(): Boolean = !isCompleted()

        fun <T : Any> getValue(step: WorkflowBackend.Step<S, *>, key: Value<T>): T {

            @Suppress("UNCHECKED_CAST")
            return when {
                data.containsKey(key.name) -> data[key.name]!! as? T ?: key.default
                else -> key.default
            }
        }
    }

    val workflowId: WorkflowId

    val state: WorkflowState

    val activeStages: Set<StageId>

    val stages: Map<String, StageData<S>>

    /**
     * Returns true when the whole workflow is completed
     */
    fun areAllStagesCompleted(): Boolean = getIncompleteStages().isEmpty()

    /**
     * Returns false when the whole workflow is completed
     */
    fun areNotAllStagesCompleted(): Boolean = !areAllStagesCompleted()

    /**
     * Returns a list of all incomplete stages
     */
    fun getIncompleteStages(): List<StageData<S>> {
        return stages
            .map { (_, stage) -> stage }
            .filter { stage -> areNotAllStepsCompleted(stage.id) }
    }

    /**
     * Gets the [StageData] for the given [stageId]
     */
    fun getStage(stageId: StageId): StageData<S>? = stages[stageId.id]

    /**
     * Gets the [StageData] for the given [stage]
     */
    fun getStage(stage: WorkflowBackend.Stage<S>): StageData<S>? = getStage(stageId = stage.id)

    /**
     * Return true when the stage with the given [stageId] is completed
     */
    fun areAllStepsCompleted(stageId: StageId): Boolean {
        return getIncompleteSteps(stageId).isEmpty()
    }

    /**
     * Return false when the stage with the given [stageId] is completed
     */
    fun areNotAllStepsCompleted(stageId: StageId): Boolean = !areAllStepsCompleted(stageId)

    /**
     * Return a list of all stages that are not yet completed
     */
    fun getIncompleteSteps(stageId: StageId): List<StepData<S>> {

        val stage = getStage(stageId) ?: return emptyList()

        return stage
            .steps
            .filterValues { it.isNotCompleted() }
            .values
            .toList()
    }
}
