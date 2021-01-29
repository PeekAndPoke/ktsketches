package de.peekandpoke.kraft.addons.styling

import kotlinx.css.CSSBuilder
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.style

data class InlineStyle(val css: String)

fun inlineStyle(block: CSSBuilder.() -> Unit): InlineStyle = InlineStyle(CSSBuilder().apply(block).toString())

fun CommonAttributeGroupFacade.css(block: CSSBuilder.() -> Unit) = css(CSSBuilder().apply(block))

fun CommonAttributeGroupFacade.css(builder: CSSBuilder) {
    style = builder.toString()
}

fun CommonAttributeGroupFacade.css(css: InlineStyle) {
    style = css.css
}

