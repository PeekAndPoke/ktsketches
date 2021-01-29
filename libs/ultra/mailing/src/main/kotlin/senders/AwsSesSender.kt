package de.peekandpoke.ultra.mailing.senders

import de.peekandpoke.ultra.mailing.Email
import de.peekandpoke.ultra.mailing.EmailSender
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.ses.SesClient
import software.amazon.awssdk.services.ses.model.Body
import software.amazon.awssdk.services.ses.model.SendEmailRequest

class AwsSesSender(private val client: SesClient) : EmailSender {

    companion object {
        fun of(config: AwsSesConfig): AwsSesSender {

            val sesClient = SesClient.builder()
                .region(
                    Region.of(config.region)
                )
                .credentialsProvider {
                    AwsBasicCredentials.create(config.accessKeyId, config.secretAccessKey)
                }
                .build()

            return AwsSesSender(sesClient)
        }
    }

    override fun send(email: Email): EmailSender.Result {

        val request = prepare(email) {

            when (email.body) {
                is Email.Body.Html -> html {
                    it.data(email.body.content)
                }

                else -> text {
                    it.data(email.body.content)
                }
            }
        }

        return try {
            EmailSender.Result.ofMessageId(
                client.sendEmail(request).messageId()
            )
        } catch (error: Throwable) {
            EmailSender.Result.ofError(error)
        }
    }

    private fun prepare(email: Email, body: Body.Builder.() -> Unit): SendEmailRequest {

        val destination = email.destination

        return SendEmailRequest.builder()
            .source(email.source)
            .destination {
                it.toAddresses(destination.toAddresses)
                    .ccAddresses(destination.ccAddresses)
                    .bccAddresses(destination.bccAddresses)
            }
            .message { msg ->
                msg.subject { it.data(email.subject) }

                msg.body {
                    it.body()
                }
            }
            .build()
    }
}
