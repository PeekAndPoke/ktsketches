package de.peekandpoke.ktorfx.upnext.example

import de.peekandpoke.ktorfx.upnext.shared.WorkflowDescription
import de.peekandpoke.ktorfx.upnext.shared.WorkflowId

data class AddressData(
    val address: String
)

object BookingFlow : WorkflowDescription<BookingFlow.Subject> {

    interface Subject {
        val id: String
        val name: String
        val email: String
        val address: String
    }

    override val id = WorkflowId("thebase::booking-flow")

    override val entryPoints get() = listOf(BookedStage.id)

    override val stages
        get() = listOf<WorkflowDescription.Stage>(
            BookedStage
        )

    object BookedStage : WorkflowDescription.SimpleStage("Booked") {

        val sendAccountActivationEmail = step("send-account-activation-email")

        val sendConfirmationEmail = step("send-confirmation-email")

        val setCustomerAddress = step<AddressData>("set-customer-address")

        val waitForCompletion = step("wait-for-completion")

        val countForCompletion = step("count-for-completion")
    }
}

