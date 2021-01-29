package de.peekandpoke.kraft.addons.styling

import kotlinx.browser.document
import kotlinx.css.CSSBuilder
import org.w3c.dom.get

object StyleSheets {

    private val mounted = mutableSetOf<String>()

    private val body by lazy(LazyThreadSafetyMode.NONE) {
        document.getElementsByTagName("body")[0]
    }

    fun mount(style: StyleSheetDefinition) {

        val css = style.css

        if (!mounted.contains(css)) {
            mounted.add(css)

            val styleNode = document.createElement("style")
            styleNode.setAttribute("id", "injected-${css.hashCode()}")
            styleNode.textContent = css

//            console.log("body", body)
//            console.log("style", styleNode)

            body?.appendChild(styleNode)
        }
    }
}

interface StyleSheetDefinition {
    val css: String
}

data class RawStyleSheet(override val css: String) : StyleSheetDefinition

abstract class StyleSheet : StyleSheetDefinition {

    override val css: String
        get() = builder.toString()

    private val builder = CSSBuilder()

    fun cls(cssClassName: String, block: CSSBuilder.() -> Unit): String {

        builder.apply {
            rule(".$cssClassName") {
                block()
            }
        }

        return cssClassName
    }
}
