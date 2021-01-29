package de.peekandpoke.kraft.addons.utils

import kotlinx.browser.window
import org.w3c.dom.Window

/**
 * Helper function for a nicer use of [Window.setTimeout]
 *
 * @return The timer id
 */
fun setTimeout(timeMs: Int, block: () -> Unit): Int {
    return window.setTimeout(block, timeMs)
}

/**
 * Helper class implementing a debouncing timer.
 *
 * The block() passed to [schedule] will only be executed after [timeMs]
 * if no other call to [schedule] occurred in the meanwhile.
 */
class DebouncingTimer(private val timeMs: Int, private val exceptFirst: Boolean) {

    private var counter = 0
    private var timerId: Int? = null

    operator fun invoke(block: () -> Unit) {
        if (counter++ == 0 && exceptFirst) {
            block()
        } else {
            schedule(block)
        }
    }

    fun schedule(block: () -> Unit) {
        timerId?.let { window.clearTimeout(it) }

        timerId = setTimeout(timeMs) {
            timerId = null
            block()
        }
    }
}
