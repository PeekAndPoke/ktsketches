package de.peekandpoke.ultra.mailing

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML

/**
 * Base interface for all email messages
 */
data class Email(
    /** The sender email address */
    val source: String,
    /** The destination addresses*/
    val destination: Destination,
    /** The email subject */
    val subject: String,
    /** The email body */
    val body: Body,
) {
    sealed class Body {

        abstract val content: String

        data class Text(override val content: String) : Body()

        data class Html(override val content: String) : Body() {

            companion object {
                operator fun invoke(block: HTML.() -> Unit) = Html(
                    content = createHTML().html { block() }
                )
            }
        }
    }

    /**
     * Email destinations
     */
    data class Destination(
        val toAddresses: List<String>,
        val ccAddresses: List<String> = listOf(),
        val bccAddresses: List<String> = listOf()
    ) {
        companion object {
            fun to(address: String) = Destination(
                toAddresses = listOf(address)
            )
        }
    }
}
