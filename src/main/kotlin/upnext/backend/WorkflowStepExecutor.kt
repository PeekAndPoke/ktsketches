package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.WorkflowData
import de.peekandpoke.ktorfx.upnext.shared.WorkflowState
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import java.time.Instant
import kotlin.math.max
import kotlin.math.min

class WorkflowStepExecutor<S>(
    val stage: WorkflowBackend.Stage<S>,
    val step: WorkflowBackend.Step<S, *>,
    subject: S,
    val workflowData: MutableWorkflowData<S>,
) {
    var subject: S = subject
        private set

    val stageData: MutableWorkflowData.MutableStageData<S>
        get() = workflowData.getStage(stage)

    val stepData: MutableWorkflowData.MutableStepData<S>
        get() = stageData.getStep(step)

    fun setState(state: WorkflowState) {
        stepData.apply {
            setState(state)
            addLog(logEntry(state = state))
        }
    }

    fun markAsOpen(): Unit = setState(WorkflowState.Open)

    fun markProgress(current: Number, target: Number): Unit =
        markProgress(current.toDouble() / target.toDouble())

    fun markProgress(progress: Double): Unit = setState(WorkflowState.Progress(max(0.0, min(progress, 1.0))))

    fun markAsDone(): Unit = setState(WorkflowState.Done)

    fun markAsSkipped(): Unit = setState(WorkflowState.Skipped)

    fun markAsFailed(): Unit = setState(WorkflowState.Failed)

    fun <T : Any> WorkflowData.Value<T>.modify(modify: (T) -> T): T = set(modify(get()))

    fun <T : Any> WorkflowData.Value<T>.get(): T = getValue(this)

    fun <T : Any> getValue(key: WorkflowData.Value<T>): T = stepData.getValue(step, key)

    fun <T : Any> WorkflowData.Value<T>.set(value: T): T = setValue(this, value)

    fun <T : Any> setValue(key: WorkflowData.Value<T>, value: T): T = value.also {
        stepData.apply {
            setValue(key, value)
            addLog(logEntry(key = key, value = value))
        }
    }

    fun modifySubject(block: (S) -> S) {
        subject = block(subject)
    }

    /**
     * Adds a log to the step
     */
    fun comment(comment: String) {
        stepData.addLog(
            WorkflowData.LogEntry.Comment(ts = now(), comment = comment)
        )
    }

    private fun logEntry(state: WorkflowState) =
        WorkflowData.LogEntry.StateChange(ts = now(), state = state)

    private fun <T : Any> logEntry(key: WorkflowData.Value<T>, value: T) =
        WorkflowData.LogEntry.ValueChange(ts = now(), key = key.name, value = value.toString())

    private fun now() = PortableDateTime(Instant.now().toEpochMilli())
}
