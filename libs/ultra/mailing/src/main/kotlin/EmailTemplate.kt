package de.peekandpoke.ultra.mailing

import kotlinx.html.HTML
import kotlinx.html.html
import kotlinx.html.stream.createHTML

/**
 * An [EmailTemplate] produces and [Email]
 */
interface EmailTemplate {
    /** Produces the [Email] to be sent */
    fun toEmail(): Email

    fun textBody(block: () -> String): Email.Body.Text {
        return Email.Body.Text(
            content = block()
        )
    }

    fun htmlBody(block: HTML.() -> Unit): Email.Body.Html {
        return Email.Body.Html(
            content = createHTML().html { block() }
        )
    }
}
