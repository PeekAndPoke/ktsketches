package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.SubjectId

interface WorkflowSubjectRepository<S> {

    fun loadWorkflowSubject(id: SubjectId): S?

    fun saveWorkflowSubject(subject: S): SubjectId

    fun saveWorkflowSubjectIfChanged(initial: S, result: S): SubjectId

    /**
     * This is an in memory implementation of a [WorkflowSubjectRepository].
     *
     * NOTICE: Whenever the application stops all data will be lost!
     *
     * This should probably only used for trying out things.
     */
    class InMemory<S>(
        val subjectToId: (S) -> SubjectId,
    ) : WorkflowSubjectRepository<S> {

        private val entries = mutableMapOf<SubjectId, S>()

        override fun loadWorkflowSubject(id: SubjectId): S? {
            return entries[id]
        }

        override fun saveWorkflowSubject(subject: S): SubjectId {

            return subjectToId(subject)
                .also { entries[it] = subject }
        }

        override fun saveWorkflowSubjectIfChanged(initial: S, result: S): SubjectId {
            return when {
                initial != result -> saveWorkflowSubject(result)
                else -> subjectToId(result)
            }
        }
    }
}
