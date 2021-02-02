package de.peekandpoke.ktorfx.upnext.example

import de.peekandpoke.ktorfx.upnext.backend.*
import de.peekandpoke.ktorfx.upnext.shared.SubjectId
import de.peekandpoke.ktorfx.upnext.shared.WorkflowData
import de.peekandpoke.ultra.slumber.Codec
import kotlinx.coroutines.delay
import java.time.Instant

data class Booking(
    override val id: String,
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
        id = "booking-001",
        name = "Sir Vival",
        email = "sirvival@example.com",
        address = ""
    )

    val dataRepo = WorkflowDataRepository.InMemory<Booking>()

    val subjectRepo = WorkflowSubjectRepository.InMemory<Booking> { SubjectId(it.id) }
        .also { it.saveWorkflowSubject(booking) }

    val backend: WorkflowBackend<Booking> = createWorkflowBackend(BookingFlow) {

        with(BookingFlow.SetupStage) {
            sendAccountActivationEmail handledBy SendAccountActivationEmail
            sendConfirmationEmail handledBy SendConfirmationEmail
            waitForCompletion handledBy CompleteAfter(2500)

            transitToInput handledBy WorkflowBackend.TransitionHandler.WhenAllStepsAreCompleted()
        }

        with(BookingFlow.InputStage) {
            setCustomerAddress handledBy SetCustomerAddress

            transitToFinal handledBy WorkflowBackend.TransitionHandler.WhenAllStepsAreCompleted()
        }

        with(BookingFlow.FinalStage) {
            countForCompletion handledBy CountUntil(5)
        }
    }

    val engine: WorkflowEngine<Booking> = WorkflowEngine(
        workflow = backend,
        dataRepo = dataRepo,
        subjectRepo = subjectRepo,
    )

    var result = engine.runAutomatic(
        SubjectId(booking.id)
    )

    println(result.data)
    println("------------------------------------------------------------------------------------------------")

    while (result.data.areNotAllStagesCompleted()) {

        result = engine.runAutomatic(SubjectId(booking.id))

        if (BookingFlow.InputStage.id in result.data.activeStages) {
            result = engine.executeStep(
                SubjectId(booking.id),
                BookingFlow.InputStage.setCustomerAddress,
                AddressData(address = "Set from external! Yeah!"),
            )
        }

        delay(750)

        println("Active stages: ${result.data.activeStages.map { it.id }}")

        val openStages = result.data.getIncompleteStages()

        println("Incomplete stages: ${openStages.map { it.id.id }}")
    }

    println("------------------------------------------------------------------------------------------------")
    println(result.subject)
    println(codec.slumber(result.data))
}
