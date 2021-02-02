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

    override val entryPoints get() = setOf(SetupStage.id)

    override val stages
        get() = listOf<WorkflowDescription.Stage>(
            SetupStage,
            InputStage,
            FinalStage,
        )

    object SetupStage : WorkflowDescription.SimpleStage("Setup") {

        val sendAccountActivationEmail = step("send-account-activation-email")

        val sendConfirmationEmail = step("send-confirmation-email")

        val waitForCompletion = step("wait-for-completion")

        val transitToInput = transition("to-input-stage") { setOf(InputStage) }
    }

    object InputStage : WorkflowDescription.SimpleStage("Input") {
        val setCustomerAddress = step<AddressData>("set-customer-address")

        val transitToFinal = transition("to-final-stage") { setOf(FinalStage) }
    }

    object FinalStage : WorkflowDescription.SimpleStage("Final") {
        val countForCompletion = step("count-for-completion")
    }
}

