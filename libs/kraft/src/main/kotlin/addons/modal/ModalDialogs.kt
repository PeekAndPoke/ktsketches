package de.peekandpoke.kraft.addons.modal

import de.peekandpoke.kraft.store.StreamSource

class ModalDialogs {

    class Handle(
        val view: ModalRenderer,
        internal val dialogs: ModalDialogs,
    ) {
        fun close() = dialogs.close(this)
    }

    private val stack = mutableListOf<Handle>()

    private val modalStream = StreamSource<Handle?>(null)

    val current = modalStream.readonly

    fun show(view: ModalRenderer): Handle {

        return Handle(view = view, dialogs = this).apply {
            stack.add(this)
            notify()
        }
    }

    fun close(handle: Handle) {
        stack.remove(handle)
        notify()
    }

    fun closeAll() {
        stack.clear()
        notify()
    }

    private fun notify() {
        if (stack.isNotEmpty()) {
            modalStream(stack.last())
        } else {
            modalStream(null)
        }
    }
}

