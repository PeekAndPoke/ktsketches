package de.peekandpoke.ultra.mailing

interface Mailing {
    fun send(email: Email): EmailSender.Result

    fun send(email: EmailTemplate): EmailSender.Result
}

class SimpleMailing(private val sender: EmailSender) : Mailing {

    override fun send(email: EmailTemplate): EmailSender.Result {
        return send(email.toEmail())
    }

    override fun send(email: Email): EmailSender.Result {
        return sender.send(email)
    }
}

