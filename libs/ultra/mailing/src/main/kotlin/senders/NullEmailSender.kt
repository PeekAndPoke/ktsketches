package de.peekandpoke.ultra.mailing.senders

import de.peekandpoke.ultra.mailing.Email
import de.peekandpoke.ultra.mailing.EmailSender

class NullEmailSender : EmailSender {

    override fun send(email: Email): EmailSender.Result {
        return EmailSender.Result(success = false)
    }
}
