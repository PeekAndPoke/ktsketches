package de.peekandpoke.ktorfx.upnext.shared

import kotlinx.serialization.SerialName

sealed class WorkflowState {

    interface FinalState

    // NON-Final states ////////////////////////////////////////////

    @SerialName("open")
    object Undefined : WorkflowState() {
        override fun toString() = "open"
    }

    @SerialName("open")
    object Open : WorkflowState() {
        override fun toString() = "open"
    }

    @SerialName("progress")
    data class Progress(val progress: Double) : WorkflowState() {
        override fun toString() = "progress: $progress"
    }

    // FINAL states ////////////////////////////////////////////////

    @SerialName("done")
    object Done : WorkflowState(), FinalState {
        override fun toString() = "done"
    }

    @SerialName("skipped")
    object Skipped : WorkflowState(), FinalState {
        override fun toString() = "skipped"
    }

    @SerialName("failed")
    object Failed : WorkflowState(), FinalState {
        override fun toString() = "failed"
    }
}
