package de.peekandpoke.kraft.addons.validation

open class NonNull(
    override val message: String = "Must not be empty"
) : Rule<Any?> {

    companion object : NonNull()

    override fun invoke(value: Any?): Boolean {
        return value != null
    }
}

class EqualTo<T>(
    val input: () -> T,
    val msg: EqualTo<T>.() -> String = defaultMessage()
) : Rule<T> {

    companion object {
        fun <T> defaultMessage(): EqualTo<T>.() -> String = { "Must be equal to '${input()}'" }

        operator fun <T> invoke(input: T, message: String) =
            EqualTo({ input }, { message })
    }

    override fun invoke(value: T): Boolean {
        return value == input()
    }

    override val message: String
        get() = msg()
}

class NotEqualTo<T>(
    val input: () -> T,
    val msg: NotEqualTo<T>.() -> String = defaultMessage()
) : Rule<T> {

    companion object {
        fun <T> defaultMessage(): NotEqualTo<T>.() -> String = { "Must not be equal to '${input()}'" }

        operator fun <T> invoke(input: T, message: String) =
            NotEqualTo({ input }, { message })
    }

    override fun invoke(value: T): Boolean {
        return value != input()
    }

    override val message: String
        get() = msg()
}
