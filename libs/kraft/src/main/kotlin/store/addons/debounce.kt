package de.peekandpoke.kraft.store.addons

import de.peekandpoke.kraft.addons.utils.DebouncingTimer
import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.store.StreamWrapper

/**
 * Debounce the incoming values by the given [timeMs]
 */
fun <T> Stream<T>.debounce(timeMs: Int, exceptFirst: Boolean = true): Stream<T> = StreamDebounce(this, timeMs, exceptFirst)

fun debouncedFunc(timeMs: Int = 50, block: () -> Unit): () -> Unit {
    val timer = DebouncingTimer(timeMs, false)

    return {
        timer.invoke(block)
    }
}

/**
 * Implementation of a debouncing stream
 */
private class StreamDebounce<T>(wrapped: Stream<T>, timeMs: Int, exceptFirst: Boolean) : StreamWrapper<T>(wrapped) {

    private val debounce = DebouncingTimer(timeMs, exceptFirst)

    override fun handleIncoming(value: T) {
        debounce {
            super.handleIncoming(value)
        }
    }
}
