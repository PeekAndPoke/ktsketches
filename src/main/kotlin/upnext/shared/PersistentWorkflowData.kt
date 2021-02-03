package de.peekandpoke.ktorfx.upnext.shared

import de.peekandpoke.ultra.common.datetime.PortableDateTime
import upnext.shared.WorkflowLogEntry
import java.time.Instant

data class PersistentWorkflowData<S>(
    /** The id of this workflow data */
    override val dataId: WorkflowData.Id,
    /** The id of data [S] that the workflow is working on */
    val subjectId: SubjectId,
    /** The creation date of the workflow */
    val createdAt: PortableDateTime = PortableDateTime(Instant.now().toEpochMilli()),
    /** The id of the associated workflow */
    override val workflowId: WorkflowId,
    /** The state of the workflow */
    override val state: WorkflowState,
    /** The currently active stages of the workflow */
    override val activeStages: Set<StageId>,
    /** All stages of the workflow */
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
