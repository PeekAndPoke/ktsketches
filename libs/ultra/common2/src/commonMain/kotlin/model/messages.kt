package de.peekandpoke.ultra.common.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageCollection(
    val title: String,
    val messages: List<Message>,
    val children: List<MessageCollection>? = null
) {
    /**
     * Get all messages recursively.
     *
     * Includes:
     *  - the [messages]
     *  - and all message of all [children]
     *
     */
    fun getAllMessages(): List<Message> {

        return mutableListOf<Message>()
            .apply { addAll(messages) }
            .apply { children?.let { children -> addAll(children.flatMap { it.getAllMessages() }) } }
            .toList()
    }

    /**
     * The type of the worst message in the entire collection.
     *
     * Taking into account:
     * - the [messages]
     * - the messages of all [children]
     */
    fun getWorstMessageType(): Message.Type {
        val worstInMessages = messages.map { it.type }.getWorst()

        val worstInChildren = children?.map { it.getWorstMessageType() }.getWorst()

        return listOf(worstInMessages, worstInChildren).getWorst()
    }

    /**
     * Returns 'true' when none of the messages is a [Message.Type.warning] or [Message.Type.error]
     */
    val isSuccess
        get() =
            (getWorstMessageType() == Message.Type.info)

    /**
     * Returns 'true' when none of the messages is a [Message.Type.error]
     */
    val isWarningOrBetter
        get() =
            getWorstMessageType() in listOf(Message.Type.warning, Message.Type.info)

    /**
     * Returns 'true' when at least one the messages is a [Message.Type.error]
     */
    val isError
        get() =
            getWorstMessageType() == Message.Type.error

    /**
     * Gets the worst message type out of the list of type
     */
    private fun List<Message.Type>?.getWorst(): Message.Type {

        val set = this?.toSet() ?: emptySet()

        return when {
            set.contains(Message.Type.error) -> Message.Type.error
            set.contains(Message.Type.warning) -> Message.Type.warning
            else -> Message.Type.info
        }
    }
}

@Serializable
data class Message(
    val type: Type,
    val text: String
) {
    companion object {
        fun info(text: String) = Message(Type.info, text)
        fun warning(text: String) = Message(Type.warning, text)
        fun error(text: String) = Message(Type.error, text)
    }

    @Suppress("EnumEntryName")
    enum class Type {
        info,
        warning,
        error
    }
}
