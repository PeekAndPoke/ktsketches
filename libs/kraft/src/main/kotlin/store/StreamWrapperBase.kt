package de.peekandpoke.kraft.store

/**
 * Base class for all stream wrappers
 */
abstract class StreamWrapperBase<WRAPPED, RESULT>(
    /** The wrapped stream */
    private val wrapped: Stream<WRAPPED>,
) : Stream<RESULT> {

    /** Unsubscribe function for the subscription on the wrapped stream */
    private var wrappedUnsubscribe: Unsubscribe? = null

    /** All subscriptions to this stream */
    private val subscriptions = mutableSetOf<StreamHandler<RESULT>>()

    /**
     * Handles the next incoming value of the wrapped stream
     */
    protected abstract fun handleIncoming(value: WRAPPED)

    /**
     * Adds a subscription to the stream.
     *
     * On subscribing the subscription is immediately called with the current value.
     *
     * Returns an unsubscribe function. Calling this function unsubscribes from the stream.
     *
     * NOTICE: It is the callers obligation to unsubscribe from the stream.
     */
    override fun subscribeToStream(sub: StreamHandler<RESULT>): Unsubscribe {
        this.subscriptions.add(sub)

        // Subscribe to the wrapped stream if necessary
        if (wrappedUnsubscribe == null) {
            console.log("Subscribing to wrapped")
            wrappedUnsubscribe = wrapped.subscribeToStream {
                handleIncoming(it)
            }
        }

        sub(invoke())

        return {
            subscriptions.remove(sub)

            // Unsubscribe from the wrapped stream if necessary
            if (subscriptions.isEmpty()) {
                console.log("Ub-Subscribing from wrapped")
                wrappedUnsubscribe!!()
                wrappedUnsubscribe = null
            }
        }
    }

    /**
     * Publishes the next value
     */
    protected fun publish(value: RESULT) {
        subscriptions.forEach { it(value) }
    }
}
