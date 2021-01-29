package de.peekandpoke.kraft.addons.semanticui.forms.textarea

import de.peekandpoke.kraft.addons.utils.identity
import de.peekandpoke.kraft.components.comp
import kotlinx.html.InputType
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.TextArea(
    value: String,
    onChange: (String) -> Unit,
    configure: TextAreaFieldComponent.Config<String>.() -> Unit = {}
) =
    TextAreaFieldComponent.Config(
        value = value,
        type = InputType.text,
        onChange = onChange,
        toStr = ::identity,
        fromStr = ::identity
    ).apply(configure)
        .let { config -> comp(config.asProps) { ctx -> TextAreaFieldComponent(ctx) } }

