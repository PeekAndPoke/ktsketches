package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.WorkflowData
import de.peekandpoke.ktorfx.upnext.shared.WorkflowState
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import kotlinx.coroutines.runBlocking
import java.time.Instant
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberExtensionFunctions
import kotlin.reflect.full.isSupertypeOf


class WorkflowStepExecutor<S>(
    val stage: WorkflowBackend.Stage<S>,
    val step: WorkflowBackend.Step<S, *>,
    val workflowData: MutableWorkflowData<S>,
) {
    companion object {
        fun <S> runBlocking(
            stage: WorkflowBackend.Stage<S>,
            step: WorkflowBackend.Step<S, *>,
            workflowData: MutableWorkflowData<S>,
            block: suspend WorkflowStepExecutor<S>.() -> Unit
        ) = runBlocking {
            WorkflowStepExecutor(
                stage = stage,
                step = step,
                workflowData = workflowData
            ).also {
                it.block()
            }
        }

        fun <S> runBlocking(
            stage: WorkflowBackend.Stage<S>,
            step: WorkflowBackend.Step<S, *>,
            workflowData: MutableWorkflowData<S>,
            handler: WorkflowBackend.StepHandler.Automatic<S>
        ) {
            runBlocking(stage = stage, step = step, workflowData = workflowData) {
                handler.apply { execute() }
            }
        }

        fun <S> runBlocking(
            stage: WorkflowBackend.Stage<S>,
            step: WorkflowBackend.Step<S, *>,
            workflowData: MutableWorkflowData<S>,
            handler: WorkflowBackend.StepHandler.Interactive<S, *>,
            stepData: Any,
        ) {
            // get the 'execute' method of the interactive handler
            val executeMethod = handler::class.declaredMemberExtensionFunctions.first { it.name == "execute" }
            // get the type of the stepData
            val stepDataType = stepData::class.createType()
            // 0. param: Instance parameter
            // 1. param: Receiver parameter
            // 2. param: The data param of the execute method.
            val dataParam = executeMethod.parameters[2]

            // Check if the given data can be used to call the execute method
            if (dataParam.type.isSupertypeOf(stepDataType)) {
                runBlocking(stage = stage, step = step, workflowData = workflowData) {
                    @Suppress("UNCHECKED_CAST")
                    (handler as WorkflowBackend.StepHandler.Interactive<S, Any>).apply {
                        execute(stepData)
                    }
                }
            } else {
                // TODO: log some message on the workflow itself
                println("ERROR: incompatible data of type '$stepDataType'")
            }
        }
    }

    val subject get() = workflowData.subject

    private val stageData: MutableWorkflowData.MutableStageData<S>
        get() = workflowData.getStage(stage)

    private val stepData: MutableWorkflowData.MutableStepData<S>
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

    /**
     * Applies a modification in the subject
     */
    fun modifySubject(block: (S) -> S) {
        workflowData.subject = block(workflowData.subject)
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
