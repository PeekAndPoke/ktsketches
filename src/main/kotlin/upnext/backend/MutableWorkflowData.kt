package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*
import upnext.shared.WorkflowLogEntry
import java.time.Instant


class MutableWorkflowData<S>(
    /** The id of this workflow data */
    override val dataId: WorkflowData.Id,
    subject: S,
    state: WorkflowState,
    val createdAt: Instant,
    override val workflowId: WorkflowId,
    override val activeStages: MutableSet<StageId>,
    override val stages: MutableMap<String, MutableStageData<S>>,
) : WorkflowData<S> {

    class MutableStageData<S>(
        override val id: StageId,
        override val steps: MutableMap<String, MutableStepData<S>>,
    ) : WorkflowData.StageData<S> {

        fun getStep(stepId: StepId): MutableStepData<S> = steps.getOrPut(stepId.id) {
            MutableStepData(
                id = stepId,
                state = StepState.Undefined,
                data = mutableMapOf(),
                logs = mutableListOf(),
            )
        }

        override fun getStep(step: WorkflowBackend.Step<S, *>): MutableStepData<S> = getStep(step.id)
    }

    class MutableStepData<S>(
        override val id: StepId,
        state: StepState,
        override val data: MutableMap<String, Any>,
        override val logs: MutableList<WorkflowLogEntry>,
    ) : WorkflowData.StepData<S> {

        override var state: StepState = state
            private set

        fun setState(state: StepState) {
            this.state = state
        }

        fun setStateIfUndefined(state: StepState) {
            if (this.state == StepState.Undefined) {
                setState(state)
            }
        }

        fun <T : Any> setValue(key: WorkflowData.Value<T>, value: T) {
            data[key.name] = value
        }

        fun addLog(entry: WorkflowLogEntry) {
            logs.add(entry)
        }
    }

    val initialSubject: S = subject

    var subject: S = subject
        internal set

    override var state: WorkflowState = state
        private set

    fun removeActiveStage(stageId: StageId) {
        activeStages.remove(stageId)
    }

    fun addActiveStage(stageId: StageId) {
        activeStages.add(stageId)
    }

    fun setState(newState: WorkflowState) {
        this.state = newState
    }

    override fun getStage(stageId: StageId): MutableStageData<S> = stages.getOrPut(stageId.id) {
        MutableStageData(
            id = stageId,
            steps = mutableMapOf(),
        )
    }

    override fun getStage(stage: WorkflowBackend.Stage<S>): MutableStageData<S> = getStage(stage.id)
}

