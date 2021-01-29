package de.peekandpoke.kraft.addons.semanticui.forms.input

import de.peekandpoke.kraft.components.comp
import kotlinx.html.Tag
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KMutableProperty0

@Suppress("FunctionName")
fun Tag.CheckboxField(
    prop: KMutableProperty0<Boolean>,
    configure: CheckboxFieldComponent.Config<Boolean>.() -> Unit = {}
) = CheckboxField(
    value = prop.get(),
    onChange = prop::set,
    configure = configure
)

@Suppress("FunctionName")
fun Tag.CheckboxField(
    value: Boolean,
    onChange: (Boolean) -> Unit,
    configure: CheckboxFieldComponent.Config<Boolean>.() -> Unit = {}
) = CheckboxField(
    value = value,
    options = false to true,
    onChange = onChange,
    configure = configure
)

@Suppress("FunctionName")
fun <T> Tag.CheckboxField(
    value: T,
    options: Pair<T, T>,
    onChange: (T) -> Unit,
    configure: CheckboxFieldComponent.Config<T>.() -> Unit = {}
) =
    CheckboxFieldComponent.Config(
        value = value,
        uncheckedValue = options.first,
        checkedValue = options.second,
        onChange = onChange,
    ).apply(configure)
        .let { config -> comp(config.asProps) { ctx -> CheckboxFieldComponent(ctx) } }
