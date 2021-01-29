package de.peekandpoke.kraft.addons.semanticui.forms.input

import de.peekandpoke.kraft.addons.forms.FormFieldComponent
import de.peekandpoke.kraft.addons.semanticui.forms.renderErrors
import de.peekandpoke.kraft.addons.validation.Rule
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.onChange
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultra.semanticui.SemanticTag
import de.peekandpoke.ultra.semanticui.ui
import kotlinx.html.*
import org.w3c.dom.HTMLInputElement

open class CheckboxFieldComponent<T>(ctx: Ctx<Props<T>>) : FormFieldComponent<T, CheckboxFieldComponent.Props<T>>(ctx) {

    data class Props<P>(
        override val initialValue: P,
        val config: Config<P>,
    ) : FormFieldComponent.Props<P> {
        override val fromStr: (String) -> P get() = config.fromStr
        override val onChange: (P) -> Unit get() = config.onChange
        override val rules: List<Rule<P>> get() = config.rules
    }

    class Config<T>(
        var value: T,
        var checkedValue: T,
        var uncheckedValue: T,
        val onChange: (T) -> Unit,
    ) {
        enum class Style {
            DEFAULT,
            SLIDER,
            TOGGLE
        }

        val asProps get() = Props(initialValue = value, config = this)

        /** The validation rules */
        val rules: MutableList<Rule<T>> = mutableListOf()

        /** The label renderer of the field */
        val label get() = _label

        /** The style of the checkbox */
        val style get() = _style

        /** The appearance of the input field */
        val appearance get() = _appearance

        /** Converts from string to value. */
        val fromStr: (String) -> T = {
            when (it) {
                true.toString() -> checkedValue
                else -> uncheckedValue
            }
        }

        /** The label renderer of the field */
        private var _label: LABEL.() -> Unit = {}

        /** The checkbox style */
        private var _style: Style = Style.DEFAULT

        /** The appearance of the input field */
        private var _appearance: SemanticTag.() -> SemanticTag = { this }

        /** Sets the label */
        fun label(label: String) = apply {
            _label = { +label }
        }

        /** Sets the label as a render function */
        fun label(rendered: LABEL.() -> Unit) = apply {
            _label = rendered
        }

        /** Sets the style of the checkbox */
        fun style(style: Style) = apply {
            _style = style
        }

        /** Sets the style of the checkbox to [Style.SLIDER] */
        fun slider() = style(Style.SLIDER)

        /** Sets the style of the checkbox to [Style.TOGGLE] */
        fun toggle() = style(Style.TOGGLE)

        /** Sets the appearance of the input field */
        fun appear(appearance: SemanticTag.() -> SemanticTag) = apply {
            _appearance = appearance
        }

        /** Adds a validation rule */
        fun accepts(rule: Rule<T>, vararg rules: Rule<T>) = apply {
            this.rules.add(rule)
            this.rules.addAll(rules)
        }
    }

    override fun VDom.render() {

        ui.with(props.config.appearance).given(hasErrors) { error }.field {
            div {
                ui.apply(props.config.style).checkbox {
                    input {
                        onChange {
                            val checked = (it.target as HTMLInputElement).checked
                            setInput(checked.toString())
                        }
                        type = InputType.checkBox
                        checked = currentValue == props.config.checkedValue
                    }

                    renderLabel()
                }
            }

            renderErrors(this@CheckboxFieldComponent, this)
        }
    }

    private fun FlowContent.renderLabel() {
        label {
            apply(props.config.label)
            // Implement toggle when clicking on the label
            onClick {
                setValue(
                    when (currentValue) {
                        props.config.checkedValue -> props.config.uncheckedValue
                        else -> props.config.checkedValue
                    }
                )
            }
        }
    }

    private fun SemanticTag.apply(style: Config.Style) = when (style) {
        Config.Style.DEFAULT -> this
        Config.Style.SLIDER -> slider
        Config.Style.TOGGLE -> toggle
    }
}
