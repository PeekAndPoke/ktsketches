package de.peekandpoke.kraft.dev.proceed.example

import de.peekandpoke.kraft.dev.proceed.*
import kotlinx.coroutines.delay
import java.time.Instant

data class Booking(
    override val name: String,
    override val email: String,
    override val address: String,
) : BookingFlow.Subject

object SendAccountActivationEmail : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun StepExecutor<Booking>.execute() {
        println("sending account activation email to ${subject.email}")

        markAsDone()
    }
}

object SendConfirmationEmail : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun StepExecutor<Booking>.execute() {
        println("sending reservation confirmation email to ${subject.email}")

        markAsDone()
    }
}

object SetCustomerAddress : WorkflowBackend.StepHandler.Interactive<Booking, AddressData>() {

    override suspend fun StepExecutor<Booking>.execute(data: AddressData) {
        println(
            "Executing interactive step with data: $data"
        )

        modifySubject {
            it.copy(address = data.address)
        }

        markAsDone()
    }
}

class CompleteAfter(private val millis: Long) : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun StepExecutor<Booking>.execute() {

        val deadline = data.createdAt.plusMillis(millis)

        if (deadline.isBefore(Instant.now())) {
            markAsDone()
        }
    }
}

class CountUntil(private val target: Int) : WorkflowBackend.StepHandler.Automatic<Booking>() {

    override suspend fun StepExecutor<Booking>.execute() {

        val state = 0

        if (state >= target) {
            markAsDone()
        }
    }
}


suspend fun main() {

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
            countForCompletion handledBy CountUntil(3)
        }
    }

    val engine: WorkflowEngine<Booking> = WorkflowEngine(
        workflow = backend,
        subject = booking,
        data = WorkflowData(currentStage = backend.entry)
    )

    engine.runAutomatic()

    println(engine.workflowData)
    println("------------------------------------------------------------------------------------------------")

    engine.executeStep(
        BookingFlow.BookedStage.setCustomerAddress,
        AddressData(address = "Home"),
    )

    println(engine.workflowData)
    println("------------------------------------------------------------------------------------------------")

    while (!engine.isStageCompleted(BookingFlow.BookedStage.id)) {

        val incomplete = engine.getIncompleteSteps(BookingFlow.BookedStage.id)

        println("Stage not yet completed: ${incomplete.map { it.id.id }}")

        delay(1000)

        engine.runAutomatic()
    }
}
