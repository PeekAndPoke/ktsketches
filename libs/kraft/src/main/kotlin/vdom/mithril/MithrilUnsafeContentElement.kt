package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.kraft.vdom.VDomElement
import kotlinx.html.Unsafe

class MithrilUnsafeContentElement : VDomElement, Unsafe {

    private var content = ""

    override fun String.unaryPlus() {
        content += this
    }

    override fun render(): dynamic {
        return m.trust(content)
    }
}
