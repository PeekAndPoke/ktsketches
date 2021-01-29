package de.peekandpoke.kraft.addons.forms

import de.peekandpoke.kraft.addons.validation.Rule
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx

abstract class FormFieldComponent<T, P : FormFieldComponent.Props<T>>(ctx: Ctx<P>) : Component<P>(ctx) {

    ////  PROPS  //////////////////////////////////////////////////////////////////////////////////////////////////

    interface Props<P> {
        val initialValue: P
        val fromStr: (String) -> P
        val onChange: (P) -> Unit
        val rules: List<Rule<P>>
    }

    class InputValue<T>(val value: T)

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    private var inputValue: InputValue<T>? = null

    val currentValue
        get() = when (val input = inputValue) {
            null -> props.initialValue
            else -> input.value
        }

    var touched by value(false)
    var errors by value<List<String>>(emptyList())

    val hasErrors get() = errors.isNotEmpty()

    ////  LIVE-CYCLE  /////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCreate() {
        sendMessage(FormFieldMountedMessage(this))
    }

    override fun onRemove() {
        sendMessage(FormFieldUnmountedMessage(this))
    }

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    fun setInput(input: String) {
        try {
            val newValue = props.fromStr(input)
            setValue(newValue)

        } catch (t: Throwable) {
            console.error(t)

            // TODO: how to translate this?
            errors = listOf("Invalid value")
        }
    }

    fun setValue(value: T) {
        touch()

        inputValue = InputValue(value)

        if (validate()) {
            props.onChange(currentValue)
        }
    }

    fun touch() {
        touched = true
    }

    fun validate(): Boolean {
        if (touched) {
            errors = props.rules
                .filter { !it(currentValue) }
                .map { it.message }
        }

        return errors.isEmpty()
    }

    ////  RENDERING  ////////////////////////////////////////////////////////////////////////////////////////////////
}
