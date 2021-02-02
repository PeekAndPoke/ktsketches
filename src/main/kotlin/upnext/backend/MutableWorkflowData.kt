package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*
import java.time.Instant


class MutableWorkflowData<S>(
    subject: S,
    override val activeStages: MutableList<WorkflowStageId>,
    val createdAt: Instant,
    override val stages: MutableMap<String, MutableStageData<S>>,
) : WorkflowData<S> {

    val initialSubject: S = subject

    var subject: S = subject
        internal set

    class MutableStageData<S>(
        override val id: WorkflowStageId,
        override val steps: MutableMap<String, MutableStepData<S>>
    ) : WorkflowData.StageData<S> {

        fun getStep(stepId: StepId): MutableStepData<S> = steps.getOrPut(stepId.id) {
            MutableStepData(
                id = stepId,
                state = WorkflowState.Undefined,
                data = mutableMapOf(),
                logs = mutableListOf(),
            )
        }

        override fun getStep(step: WorkflowBackend.Step<S, *>): MutableStepData<S> = getStep(step.id)
    }

    class MutableStepData<S>(
        override val id: StepId,
        state: WorkflowState,
        override val data: MutableMap<String, Any>,
        override val logs: MutableList<WorkflowData.LogEntry>,
    ) : WorkflowData.StepData<S> {

        override var state: WorkflowState = state
            private set

        fun setState(state: WorkflowState) {
            this.state = state
        }

        fun setStateIfUndefined(state: WorkflowState) {
            if (this.state == WorkflowState.Undefined) {
                setState(state)
            }
        }

        fun <T : Any> setValue(key: WorkflowData.Value<T>, value: T) {
            data[key.name] = value
        }

        fun addLog(entry: WorkflowData.LogEntry) {
            logs.add(entry)
        }
    }

    override fun getStage(stageId: WorkflowStageId): MutableStageData<S> = stages.getOrPut(stageId.id) {
        MutableStageData(
            id = stageId,
            steps = mutableMapOf()
        )
    }

    override fun getStage(stage: WorkflowBackend.Stage<S>): MutableStageData<S> = getStage(stage.id)
}

