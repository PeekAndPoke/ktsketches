package de.peekandpoke.kraft.store.addons

import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.store.StreamWrapperBase

fun <T> Stream<T>.history(capacity: Int): Stream<List<T>> = StreamHistory(this, capacity)

private class StreamHistory<T>(
    wrapped: Stream<T>,
    private val capacity: Int,
) : StreamWrapperBase<T, List<T>>(
    wrapped = wrapped
) {
    private val history = mutableListOf<T>()
    private var historyReadOnly = history.toList()

    override fun invoke(): List<T> = historyReadOnly

    override fun handleIncoming(value: T) {

        while (history.size >= capacity) {
            history.removeFirst()
        }

        history.add(value)

        historyReadOnly = history.toList()

        publish(historyReadOnly)
    }
}
