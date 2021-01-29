package de.peekandpoke.kraft.dev.proceed.example

import de.peekandpoke.kraft.dev.proceed.*

data class AddressData(
    val address: String
)

object BookingFlow : Workflow<BookingFlow.Subject> {

    interface Subject {
        val name: String
        val email: String
        val address: String
    }

    override val id = WorkflowId("thebase::booking-flow")

    override val entry get() = BookedStage.id

    override val stages get() = listOf<Workflow.Stage>(
        BookedStage
    )

    object BookedStage: Workflow.SimpleStage("Booked") {

        val sendAccountActivationEmail = step("send-account-activation-email")

        val sendConfirmationEmail = step("send-confirmation-email")

        val setCustomerAddress = step<AddressData>("set-customer-address")

        val waitForCompletion = step("wait-for-completion")

        val countForCompletion = step("count-for-completion")
    }
}

