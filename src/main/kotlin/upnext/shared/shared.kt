package de.peekandpoke.ktorfx.upnext.shared

/**
 * Id of a workflow
 */
data class WorkflowId(val id: String)

/**
 * Id of a workflow stage
 */
data class WorkflowStageId(val id: String) {
    fun stepId(stepId: String) = StepId("$id::$stepId")
}

/**
 * Id of a workflow step
 */
data class StepId(val id: String)

/**
 * Id of a workflow subject
 */
data class SubjectId(val id: String)
