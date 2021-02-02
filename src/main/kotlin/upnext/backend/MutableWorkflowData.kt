package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.*
import java.time.Instant


class MutableWorkflowData<S>(
    override val activeStages: MutableList<StageId>,
    override val createdAt: Instant,
    override val stages: MutableMap<String, MutableStageData<S>>,
) : WorkflowData<S> {

    companion object {
        fun <S> of(src: WorkflowData<S>): MutableWorkflowData<S> = MutableWorkflowData(
            activeStages = src.activeStages.toMutableList(),
            createdAt = src.createdAt,
            stages = src.stages.mapValues { (_, stage) ->
                MutableStageData(
                    id = stage.id,
                    steps = stage.steps.mapValues { (_, step) ->
                        MutableStepData<S>(
                            id = step.id,
                            state = step.state,
                            data = step.data.toMutableMap(),
                            logs = step.logs.toMutableList(),
                        )
                    }.toMutableMap()
                )
            }.toMutableMap()
        )
    }

    class MutableStageData<S>(
        override val id: StageId,
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

        fun <T : Any> setValue(key: WorkflowData.Value<T>, value: T) {
            data[key.name] = value
        }

        fun addLog(entry: WorkflowData.LogEntry) {
            logs.add(entry)
        }
    }

    fun getStage(stageId: StageId): MutableStageData<S> = stages.getOrPut(stageId.id) {
        MutableStageData(
            id = stageId,
            steps = mutableMapOf()
        )
    }

    override fun getStage(stage: WorkflowBackend.Stage<S>): MutableStageData<S> = getStage(stage.id)

    fun toPersistent(): PersistentWorkflowData<S> = PersistentWorkflowData(
        activeStages = activeStages.toList(),
        createdAt = createdAt,
        stages = stages.mapValues { (_, stage) ->
            PersistentWorkflowData.PersistentStageData(
                id = stage.id,
                steps = stage.steps.mapValues { (_, step) ->
                    PersistentWorkflowData.PersistentStepData(
                        id = step.id,
                        state = step.state,
                        data = step.data.toMap(),
                        logs = step.logs.toList(),
                    )
                }
            )
        },
    )
}

