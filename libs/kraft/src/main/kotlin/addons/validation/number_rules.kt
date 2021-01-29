package de.peekandpoke.kraft.addons.validation

class InRange(private val from: Number, private val to: Number) : Rule<Number> {

    override fun invoke(value: Number): Boolean {
        return from.toDouble() <= value.toDouble() && value.toDouble() <= to.toDouble()
    }

    override val message: String
        get() = "Must be in range $from .. $to"
}

class GreaterThan(private val minimum: Number) : Rule<Number> {

    override fun invoke(value: Number): Boolean {
        return minimum.toDouble() < value.toDouble()
    }

    override val message: String
        get() = "Must be greater then $minimum"
}

class GreaterThanOrEqual(private val minimum: Number) : Rule<Number> {

    override fun invoke(value: Number): Boolean {
        return minimum.toDouble() <= value.toDouble()
    }

    override val message: String
        get() = "Must be greater than or equal to $minimum"
}

class LessThan(private val maximum: Number) : Rule<Number> {

    override fun invoke(value: Number): Boolean {
        return maximum.toDouble() > value.toDouble()
    }

    override val message: String
        get() = "Must be less then $maximum"
}

class LessThanOrEqual(private val maximum: Number) : Rule<Number> {

    override fun invoke(value: Number): Boolean {
        return maximum.toDouble() >= value.toDouble()
    }

    override val message: String
        get() = "Must be less than or equal to $maximum"
}
