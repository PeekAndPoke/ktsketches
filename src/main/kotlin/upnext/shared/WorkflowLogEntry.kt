package upnext.shared

import de.peekandpoke.ktorfx.upnext.shared.StepState
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import kotlinx.serialization.SerialName

sealed class WorkflowLogEntry {

    abstract val ts: PortableDateTime

    @SerialName("state-change")
    data class StateChange(
        override val ts: PortableDateTime,
        val state: StepState
    ) : WorkflowLogEntry()

    @SerialName("value-change")
    data class ValueChange(
        override val ts: PortableDateTime,
        val key: String,
        val value: String,
    ) : WorkflowLogEntry()

    @SerialName("comment")
    data class Comment(
        override val ts: PortableDateTime,
        val comment: String,
    ) : WorkflowLogEntry()
}
