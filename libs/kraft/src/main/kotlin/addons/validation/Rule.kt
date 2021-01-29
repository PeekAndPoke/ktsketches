package de.peekandpoke.kraft.addons.validation

interface Rule<in T> {
    operator fun invoke(value: T): Boolean

    val message: String
}

infix fun <T> Rule<T>.or(other: Rule<T>): Rule<T> = OrRule(listOf(this, other))

class OrRule<T>(private val rules: List<Rule<T>>) : Rule<T> {

    override fun invoke(value: T): Boolean {
        return rules.any { it(value) }
    }

    override val message = rules
        .filter { it.message.isNotEmpty() }
        .joinToString(" or ") { it.message }
}
