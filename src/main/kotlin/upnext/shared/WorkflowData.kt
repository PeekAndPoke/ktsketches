package de.peekandpoke.ktorfx.upnext.shared

import de.peekandpoke.ktorfx.upnext.backend.WorkflowBackend
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import kotlinx.serialization.SerialName

interface WorkflowData<S> {

    data class Value<T : Any>(val name: String, val default: T) {
        override fun toString() = name
    }

    sealed class LogEntry {
        abstract val ts: PortableDateTime

        @SerialName("state-change")
        data class StateChange(
            override val ts: PortableDateTime,
            val state: WorkflowState
        ) : LogEntry()

        @SerialName("value-change")
        data class ValueChange(
            override val ts: PortableDateTime,
            val key: String,
            val value: String,
        ) : LogEntry()

        @SerialName("comment")
        data class Comment(
            override val ts: PortableDateTime,
            val comment: String,
        ) : LogEntry()
    }

    interface StageData<S> {
        val id: StageId
        val steps: Map<String, StepData<S>>

        fun getStep(step: WorkflowBackend.Step<S, *>): StepData<S>? = steps[step.id.id]
    }

    interface StepData<S> {
        val id: StepId
        val state: WorkflowState
        val data: Map<String, Any>
        val logs: List<LogEntry>

        fun isCompleted(): Boolean = state is WorkflowState.FinalState

        fun isNotCompleted(): Boolean = !isCompleted()

        fun <T : Any> getValue(step: WorkflowBackend.Step<S, *>, key: Value<T>): T {

            @Suppress("UNCHECKED_CAST")
            return when {
                data.containsKey(key.name) -> data[key.name]!! as? T ?: key.default
                else -> key.default
            }
        }
    }

    val activeStages: Set<StageId>

    val stages: Map<String, StageData<S>>

    fun getStage(stageId: StageId): StageData<S>? = stages[stageId.id]

    fun getStage(stage: WorkflowBackend.Stage<S>): StageData<S>? = getStage(stageId = stage.id)

    fun isCompleted(stageId: StageId): Boolean {
        return getIncompleteSteps(stageId).isEmpty()
    }

    fun isNotCompleted(stageId: StageId): Boolean = !isCompleted(stageId)

    fun getIncompleteSteps(stageId: StageId): List<StepData<S>> {

        val stage = getStage(stageId) ?: return emptyList()

        return stage
            .steps
            .filterValues { it.isNotCompleted() }
            .values
            .toList()
    }
}
