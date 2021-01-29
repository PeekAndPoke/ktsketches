package de.peekandpoke.kraft.addons.semanticui.forms.input

import com.soywiz.klock.parse
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.ultra.common.datetime.PortableDate
import de.peekandpoke.ultra.common.datetime.date
import de.peekandpoke.ultra.common.datetime.portable
import kotlinx.html.InputType
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.DateField(
    value: PortableDate,
    onChange: (PortableDate) -> Unit,
    configure: InputFieldComponent.Config<PortableDate>.() -> Unit = {}
) =
    InputFieldComponent.Config(
        value = value,
        type = InputType.date,
        onChange = onChange,
        toStr = { it.date.format(DateFieldComponent.browserFormat) },
        fromStr = { DateFieldComponent.browserFormat.parse(it).local.date.portable }
    ).apply(configure)
        .let { config -> comp(config.asProps) { ctx -> DateFieldComponent(ctx) } }


@Suppress("FunctionName")
fun Tag.DateFieldNullable(
    value: PortableDate?,
    onChange: (PortableDate?) -> Unit,
    configure: InputFieldComponent.Config<PortableDate?>.() -> Unit = {}
) =
    InputFieldComponent.Config(
        value = value,
        type = InputType.date,
        onChange = onChange,
        toStr = { it?.date?.format(DateFieldComponent.browserFormat) ?: "" },
        fromStr = { DateFieldComponent.browserFormat.parse(it).local.date.portable }
    ).apply(configure)
        .let { config -> comp(config.asProps) { ctx -> DateFieldComponent(ctx) } }

