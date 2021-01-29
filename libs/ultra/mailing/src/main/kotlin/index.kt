package de.peekandpoke.ultra.mailing

import de.peekandpoke.ultra.kontainer.Kontainer
import de.peekandpoke.ultra.kontainer.KontainerBuilder
import de.peekandpoke.ultra.kontainer.module
import de.peekandpoke.ultra.mailing.senders.NullEmailSender

fun KontainerBuilder.ultraMailing() = module(Ultra_Mailing)

val Ultra_Mailing = module {
    singleton(Mailing::class, SimpleMailing::class)
    singleton(EmailSender::class, NullEmailSender::class)
}

val Kontainer.mailing get() = get(Mailing::class)
