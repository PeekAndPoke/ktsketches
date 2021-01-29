package de.peekandpoke.kraft.addons.forms

import de.peekandpoke.kraft.components.Component

open class FormController(component: Component<*>, val stopEvents: Boolean = true) {

    companion object {
        fun watcher(component: Component<*>) = FormController(component = component, stopEvents = false)
    }

    private val fields = mutableSetOf<FormFieldComponent<*, *>>()

    /**
     * Returns `true` when all fields are valid
     */
    val isValid
        get(): Boolean {
            return numErrors == 0
        }

    /**
     * Returns `true` when some fields are NOT valid
     */
    val isNotValid get() = !isValid

    /**
     * Number of fields with errors
     */
    val numErrors
        get(): Int {
            return fields.filter { !it.validate() }.size
        }

    /**
     * Validates all fields by touching them first.
     *
     * When a field is touched, it's errors will be displayed.
     */
    fun validate(): Boolean {
        fields.forEach { it.touch() }

        return isValid
    }

    init {
        component.onMessage {
            when (it) {
                is FormFieldInputChanged -> {
                    component.triggerRedraw()
                    if (stopEvents) {
                        it.stop()
                    }
                }

                is FormFieldMountedMessage -> {
                    fields.add(it.field)
                    if (stopEvents) {
                        it.stop()
                    }
                }

                is FormFieldUnmountedMessage -> {
                    fields.remove(it.sender)
                    if (stopEvents) {
                        it.stop()
                    }
                }
            }
        }
    }
}
