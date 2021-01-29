package de.peekandpoke.kraft.vdom.mithril

import org.w3c.dom.HTMLElement

@Suppress("ClassName")
@JsModule("mithril")
@JsNonModule
external object m {
    fun render(element: HTMLElement, content: dynamic): dynamic

    fun mount(element: HTMLElement, component: dynamic): dynamic

    fun trust(html: dynamic): dynamic

    fun redraw(): dynamic
}

@JsModule("mithril")
@JsNonModule
external fun m(tag: String, content: dynamic): Any

@JsModule("mithril")
@JsNonModule
external fun m(tag: dynamic, attrs: dynamic, content: dynamic): Any
