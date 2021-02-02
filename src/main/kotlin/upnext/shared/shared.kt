package de.peekandpoke.ktorfx.upnext.shared

/**
 * Id of a workflow
 */
data class WorkflowId(val id: String)

/**
 * Id of a workflow stage
 */
data class StageId(val id: String) {
    fun stepId(stepId: String) = StepId("$id::$stepId")

    fun transitionId(transitionId: String) = StageTransitionId("$id::$transitionId")
}

/**
 * Id of a stage transition
 */
data class StageTransitionId(val id: String)

/**
 * Id of a workflow step
 */
data class StepId(val id: String)

/**
 * Id of a workflow subject
 */
data class SubjectId(val id: String)
