package de.peekandpoke.ktorfx.upnext.shared

import kotlinx.serialization.SerialName

sealed class StepState {

    interface FinalState

    // NON-Final states ////////////////////////////////////////////

    @SerialName("undefined")
    object Undefined : StepState() {
        override fun toString() = "undefined"
    }

    @SerialName("open")
    object Open : StepState() {
        override fun toString() = "open"
    }

    @SerialName("progress")
    data class Progress(val progress: Double) : StepState() {
        override fun toString() = "progress: $progress"
    }

    // FINAL states ////////////////////////////////////////////////

    @SerialName("done")
    object Done : StepState(), FinalState {
        override fun toString() = "done"
    }

    @SerialName("skipped")
    object Skipped : StepState(), FinalState {
        override fun toString() = "skipped"
    }

    @SerialName("failed")
    object Failed : StepState(), FinalState {
        override fun toString() = "failed"
    }
}
