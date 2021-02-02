package de.peekandpoke.ktorfx.upnext.example

import de.peekandpoke.ktorfx.upnext.backend.WorkflowStepExecutor
import de.peekandpoke.ktorfx.upnext.backend.WorkflowBackend
import de.peekandpoke.ktorfx.upnext.backend.WorkflowEngine
import de.peekandpoke.ktorfx.upnext.backend.createWorkflowBackend
import de.peekandpoke.ktorfx.upnext.shared.PersistentWorkflowData
import de.peekandpoke.ktorfx.upnext.shared.WorkflowData
import de.peekandpoke.ultra.slumber.Codec
import kotlinx.coroutines.delay
import java.time.Instant

data class Booking(
    override val name: String,
    override val email: String,
    override val address: String,
) : BookingFlow.Subject

object SendAccountActivationEmail : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun WorkflowStepExecutor<Booking>.execute() {
        println("sending account activation email to ${subject.email}")
        comment("sending account activation email to ${subject.email}")

        markAsDone()
    }
}

object SendConfirmationEmail : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun WorkflowStepExecutor<Booking>.execute() {
        println("sending reservation confirmation email to ${subject.email}")
        comment("sending reservation confirmation email to ${subject.email}")

        markAsDone()
    }
}

object SetCustomerAddress : WorkflowBackend.StepHandler.Interactive<Booking, AddressData>() {

    override suspend fun WorkflowStepExecutor<Booking>.execute(data: AddressData) {
        println("Executing interactive step with data: $data")
        comment("Executing interactive step with data: $data")

        modifySubject {
            it.copy(address = data.address)
        }

        markAsDone()
    }
}

class CompleteAfter(private val millis: Long) : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun WorkflowStepExecutor<Booking>.execute() {

        val deadline = workflowData.createdAt.plusMillis(millis)

        if (deadline.isBefore(Instant.now())) {
            markAsDone()
        }
    }
}

class CountUntil(private val target: Int) : WorkflowBackend.StepHandler.Automatic<Booking>() {

    private val counter = WorkflowData.Value("counter", 0)

    override suspend fun WorkflowStepExecutor<Booking>.execute() {

        val state = counter.modify { it + 1 }

        println("state: $state")

        if (state >= target) {
            markAsDone()
        } else {
            markProgress(state, target)
        }
    }
}


suspend fun main() {

    val codec = Codec.default

    val booking = Booking(
        name = "Sir Vival",
        email = "sirvival@example.com",
        address = ""
    )

    val backend: WorkflowBackend<Booking> = createWorkflowBackend(BookingFlow) {

        with(BookingFlow.BookedStage) {
            sendAccountActivationEmail handledBy SendAccountActivationEmail
            sendConfirmationEmail handledBy SendConfirmationEmail
            setCustomerAddress handledBy SetCustomerAddress
            waitForCompletion handledBy CompleteAfter(2500)
            countForCompletion handledBy CountUntil(5)
        }
    }

    val engine: WorkflowEngine<Booking> = WorkflowEngine(
        workflow = backend,
        subject = booking,
        data = PersistentWorkflowData(activeStages = backend.entryPoints)
    )

    engine.runAutomatic()

    println(engine.workflowData)
    println("------------------------------------------------------------------------------------------------")

    engine.executeStep(
        BookingFlow.BookedStage.setCustomerAddress,
        AddressData(address = "Home"),
    )

    println(engine.workflowData)
    println(codec.slumber(engine.workflowData.toPersistent()))
    println("------------------------------------------------------------------------------------------------")

    while (!engine.isStageCompleted(BookingFlow.BookedStage.id)) {

        val incomplete = engine.getIncompleteSteps(BookingFlow.BookedStage.id)

        println(
            "Stage not yet completed: ${incomplete.map { "${it.id.id} -> ${it.state}" }}"
        )

        println(codec.slumber(engine.workflowData.toPersistent()))

        delay(2200)

        engine.runAutomatic()
    }
}
