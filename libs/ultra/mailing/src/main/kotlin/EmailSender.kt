package de.peekandpoke.ultra.mailing

/**
 * Definition of an email sender
 */
interface EmailSender {

    data class Result internal constructor(
        val success: Boolean,
        val messageId: String? = null,
        val attributes: Map<String, Any?> = emptyMap(),
        val error: Map<String, Any?>? = null
    ) {
        companion object {
            fun ofMessageId(messageId: String) = Result(
                success = true,
                messageId = messageId
            )

            fun ofError(error: Throwable) = Result(
                success = false,
                error = mapOf(
                    "message" to error.message,
                    "stackTrace" to (error.stackTraceToString().split("\n")),
                )
            )
        }

        fun <T> withAttribute(key: String, value: T) = copy(
            attributes = attributes.plus(key to value)
        )

        fun <T> withAttributes(vararg pairs: Pair<String, T>) = copy(
            attributes = attributes.plus(pairs)
        )
    }

    fun send(email: Email): Result
}
