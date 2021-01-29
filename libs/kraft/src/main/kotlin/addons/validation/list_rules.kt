package de.peekandpoke.kraft.addons.validation

class MinCount (
    private val len: Int,
    override val message: String = "Must have at least $len items"
) : Rule<List<*>> {

    override fun invoke(value: List<*>): Boolean {
        return value.size >= len
    }
}