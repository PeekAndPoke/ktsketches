package de.peekandpoke.kraft.addons.semanticui.forms.input

import com.soywiz.klock.DateFormat
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.onChange
import de.peekandpoke.kraft.components.onInput
import kotlinx.html.FlowContent
import kotlinx.html.input
import org.w3c.dom.HTMLInputElement

open class DateFieldComponent<T>(ctx: Ctx<Props<T>>) : InputFieldComponent<T>(ctx) {

    companion object {
        val browserFormat = DateFormat("yyyy-MM-dd")
    }

    @Suppress("DuplicatedCode")
    override fun FlowContent.renderInput() {
        input {
            onInput {
                setInput((it.target as HTMLInputElement).value)
            }
            onChange {
                setInput((it.target as HTMLInputElement).value)
            }

            type = props.config.type

            attributes["format-value"] = browserFormat.format

            this.value = props.config.toStr(currentValue)

            props.config.step?.let { step = it.toString() }

            props.config.placeholder.takeIf { it.isNotBlank() }?.let {
                placeholder = it
            }
        }
    }
}
