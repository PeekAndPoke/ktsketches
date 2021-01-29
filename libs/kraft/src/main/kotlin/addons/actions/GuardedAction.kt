package de.peekandpoke.kraft.addons.actions

import de.peekandpoke.kraft.components.Component
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

fun <T> Component<*>.guardedAction(action: () -> Flow<T>) = GuardedAction(this, action)

class GuardedAction<T>(component: Component<*>, private val action: () -> Flow<T>) {

    var isRunning by component.value(false)
        private set

    val isNotRunning get() = !isRunning

    operator fun invoke(handle: suspend (Flow<T>) -> Unit) {
        run(handle)
    }

    fun run(handle: suspend (Flow<T>) -> Unit) {

        if (isRunning) {
            return
        }

        isRunning = true

        GlobalScope.launch {
            action()
                .map {
                    isRunning = false
                    it
                }
                .catch { cause ->
                    isRunning = false
                    throw cause
                }
                .let {
                    handle(it)
                }
        }
    }
}
