package de.peekandpoke.kraft.addons.forms

import de.peekandpoke.kraft.components.MessageBase

class FormFieldInputChanged(val field: FormFieldComponent<*, *>) : MessageBase(field)

class FormFieldMountedMessage(val field: FormFieldComponent<*, *>) : MessageBase(field)

class FormFieldUnmountedMessage(val field: FormFieldComponent<*, *>) : MessageBase(field)
