package de.peekandpoke.ktorfx.upnext.backend

import com.sun.corba.se.spi.orbutil.threadpool.Work
import de.peekandpoke.ktorfx.upnext.shared.PersistentWorkflowData
import de.peekandpoke.ktorfx.upnext.shared.SubjectId

interface WorkflowDataRepository<S> {

    fun initialize(id: SubjectId, workflow: WorkflowBackend<S>): PersistentWorkflowData<S>

    fun load(id: SubjectId): PersistentWorkflowData<S>?

    fun loadOrInit(id: SubjectId, workflow: WorkflowBackend<S>): PersistentWorkflowData<S> {
        return load(id) ?: initialize(id, workflow)
    }

    fun save(data: PersistentWorkflowData<S>)

    /**
     * This is an in memory implementation of a [WorkflowDataRepository].
     *
     * NOTICE: Whenever the application stops all data will be lost!
     *
     * This should probably only used for trying out things.
     */
    class InMemory<S> : WorkflowDataRepository<S> {

        private val entries = mutableMapOf<SubjectId, PersistentWorkflowData<S>>()

        override fun initialize(id: SubjectId, workflow: WorkflowBackend<S>): PersistentWorkflowData<S> {
            return PersistentWorkflowData<S>(subjectId = id, activeStages = workflow.entryPoints)
                .also { save(it) }
        }

        override fun load(id: SubjectId): PersistentWorkflowData<S>? {
            return entries[id]
        }

        override fun save(data: PersistentWorkflowData<S>) {
            entries[data.subjectId] = data
        }
    }
}
