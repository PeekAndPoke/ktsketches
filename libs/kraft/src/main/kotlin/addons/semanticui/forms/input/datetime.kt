package de.peekandpoke.kraft.addons.semanticui.forms.input

import com.soywiz.klock.parse
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.ultra.common.datetime.PortableDateTime
import de.peekandpoke.ultra.common.datetime.date
import de.peekandpoke.ultra.common.datetime.portable
import kotlinx.html.InputType
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.DateTimeField(
    value: PortableDateTime,
    onChange: (PortableDateTime) -> Unit,
    configure: InputFieldComponent.Config<PortableDateTime>.() -> Unit = {}
) =
    InputFieldComponent.Config(
        value = value,
        type = InputType.dateTimeLocal,
        onChange = onChange,
        toStr = { it.date.format(DateTimeFieldComponent.browserFormat) },
        fromStr = { DateTimeFieldComponent.browserFormat.parse(it).local.portable }
    ).apply(configure)
        .let { config -> comp(config.asProps) { ctx -> DateTimeFieldComponent(ctx) } }
