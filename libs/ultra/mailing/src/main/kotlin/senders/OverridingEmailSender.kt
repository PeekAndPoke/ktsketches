package de.peekandpoke.ultra.mailing.senders

import de.peekandpoke.ultra.mailing.Email
import de.peekandpoke.ultra.mailing.EmailSender
import de.peekandpoke.ultra.mailing.MailingOverride

class OverridingEmailSender(
    private val override: MailingOverride,
    private val wrapped: EmailSender
) : EmailSender {

    override fun send(email: Email): EmailSender.Result {

        val overridden = override(email)

        return wrapped.send(overridden)
            .withAttributes("sender" to email.source)
            .withAttributes("destination" to email.destination)
            .let {
                if (email.destination != overridden.destination) {
                    it.withAttributes("destinationOverride" to overridden.destination)
                } else {
                    it
                }
            }
            .withAttributes("subject" to email.subject)
            .let {
                if (email.subject != overridden.subject) {
                    it.withAttributes("subjectOverride" to overridden.subject)
                } else {
                    it
                }
            }
    }
}
