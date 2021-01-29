package de.peekandpoke.kraft.store

internal class PersistentStreamSource<T>(
    private val storage: StreamStorage<T>,
    private val wrapped: StreamSource<T>
) : StreamSource<T> {

    init {
        try {
            storage.read()?.let {
                wrapped(it)
            }

        } catch (e: Throwable) {
            println(e)
        }
    }

    override fun invoke(): T = wrapped()

    override fun invoke(next: T) {

        storage.write(next)

        return wrapped(next)
    }

    override fun subscribeToStream(sub: (T) -> Unit): Unsubscribe = wrapped.subscribeToStream(sub)
}
