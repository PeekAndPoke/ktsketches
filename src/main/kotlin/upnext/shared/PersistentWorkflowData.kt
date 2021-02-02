package de.peekandpoke.ktorfx.upnext.shared

import de.peekandpoke.ultra.common.datetime.PortableDateTime
import upnext.shared.WorkflowLogEntry
import java.time.Instant

data class PersistentWorkflowData<S>(
    val subjectId: SubjectId,
    val createdAt: PortableDateTime = PortableDateTime(Instant.now().toEpochMilli()),
    override val workflowId: WorkflowId,
    override val state: WorkflowState,
    override val activeStages: Set<StageId>,
    override val stages: Map<String, PersistentStageData<S>> = emptyMap(),
) : WorkflowData<S> {

    data class PersistentStageData<S>(
        override val id: StageId,
        override val steps: Map<String, PersistentStepData<S>>,
    ) : WorkflowData.StageData<S>

    data class PersistentStepData<S>(
        override val id: StepId,
        override val state: StepState,
        override val data: Map<String, Any>,
        override val logs: List<WorkflowLogEntry>,
    ) : WorkflowData.StepData<S>
}
