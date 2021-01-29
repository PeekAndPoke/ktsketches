package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.VDomEngine
import de.peekandpoke.kraft.vdom.VDomTagConsumer
import de.peekandpoke.ultra.jshelpers.jsObjectOf
import org.w3c.dom.HTMLElement

class MithrilVDomEngine : VDomEngine {

    override fun mount(element: HTMLElement, view: VDom.() -> Any?) {

        @Suppress("UnsafeCastFromDynamic")
        val lowLevelRoot = jsObjectOf(
            "view" to { render { view() } }
        )

        m.mount(element, lowLevelRoot)
    }

    override fun createTagConsumer(host: Component<*>?): VDomTagConsumer = MithrilTagConsumer(this, host)

    override fun triggerRedraw() {
        m.redraw()
    }
}
