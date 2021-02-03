package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.PersistentWorkflowData
import de.peekandpoke.ktorfx.upnext.shared.SubjectId
import de.peekandpoke.ktorfx.upnext.shared.WorkflowData
import de.peekandpoke.ktorfx.upnext.shared.WorkflowState

interface WorkflowDataRepository<S> {

    fun initialize(id: SubjectId, workflow: WorkflowBackend<S>): PersistentWorkflowData<S>

    fun load(id: WorkflowData.Id): PersistentWorkflowData<S>?

    fun save(data: PersistentWorkflowData<S>)

    /**
     * This is an in memory implementation of a [WorkflowDataRepository].
     *
     * NOTICE: Whenever the application stops all data will be lost!
     *
     * This should probably only used for trying out things.
     */
    class InMemory<S> : WorkflowDataRepository<S> {

        private val entries = mutableMapOf<WorkflowData.Id, PersistentWorkflowData<S>>()

        override fun initialize(id: SubjectId, workflow: WorkflowBackend<S>): PersistentWorkflowData<S> {
            return PersistentWorkflowData<S>(
                dataId = WorkflowData.Id("in-memory-${entries.size + 1}"),
                subjectId = id,
                workflowId = workflow.id,
                state = WorkflowState.Open,
                activeStages = workflow.entryPoints,
            ).also { save(it) }
        }

        override fun load(id: WorkflowData.Id): PersistentWorkflowData<S>? {
            return entries[id]
        }

        override fun save(data: PersistentWorkflowData<S>) {
            entries[data.dataId] = data
        }
    }
}
