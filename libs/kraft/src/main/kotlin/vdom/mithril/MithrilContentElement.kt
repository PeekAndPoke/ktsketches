package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.kraft.vdom.VDomElement

data class MithrilContentElement(
    val content: CharSequence
) : VDomElement {
    override fun render(): dynamic {
        return content
    }
}
