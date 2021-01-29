package de.peekandpoke.kraft.addons.validation

open class Empty(override val message: String = "Must not empty") : Rule<String> {

    companion object : Empty()

    override fun invoke(value: String) = value.isEmpty()
}

open class NotEmpty(override val message: String = "Must not be empty") : Rule<String> {

    companion object : NotEmpty()

    override fun invoke(value: String) = value.isNotEmpty()
}

open class NotBlank(override val message: String = "Must not be blank") : Rule<String> {

    companion object : NotBlank()

    override fun invoke(value: String) = value.isNotBlank()
}

open class Numeric(override val message: String = "Must be a numeric value") : Rule<String> {

    companion object : Numeric()

    override fun invoke(value: String) = value.toDoubleOrNull() != null
}

open class ValidEmail(override val message: String = "Must be a valid email") : Rule<String> {

    companion object : ValidEmail() {
        // TODO: move to ultra::common-mp
        @Suppress("RegExpRedundantEscape")
        private val emailRegex =
            "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])".toRegex()
    }


    override fun invoke(value: String) = value.isNotBlank() && emailRegex.matches(value.toLowerCase())
}

class MinLength(
    private val len: Int,
    override val message: String = "Must be at least $len characters"
) : Rule<String> {

    override fun invoke(value: String): Boolean {
        return value.length >= len
    }
}

class MaxLength(
    private val len: Int,
    override val message: String = "Must be maximal $len characters"
) : Rule<String> {

    override fun invoke(value: String): Boolean {
        return value.length <= len
    }
}
