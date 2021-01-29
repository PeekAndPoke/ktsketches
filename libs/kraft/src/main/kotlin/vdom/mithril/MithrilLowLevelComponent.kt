package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.ultra.jshelpers.js
import org.w3c.dom.HTMLElement

private val symInstance = js("(Symbol('instance'))")

val MithrilLowLevelComponent = mapOf(
    // Low level hook into Mithril's 'oninit' method.
    // See: https://mithril.js.org/lifecycle-methods.html#oninit
    "oninit" to { vnode: dynamic ->
//        console.log("oninit", vnode)

        if (!vnode.state[symInstance]) {
            val instance = vnode.attrs.creator(vnode.attrs.ctx) as Component<*>
            vnode.state[symInstance] = instance
        }
    },

    // Low level hook into Mithril's 'oncreate' method.
    // See: https://mithril.js.org/lifecycle-methods.html#oncreate
    "oncreate" to { vnode: dynamic ->
        // Setting the dom node on the Component
        (vnode.state[symInstance] as Component<*>).apply {
            _setDom(vnode.dom as? HTMLElement)
            onCreate()
        }
    },

    // Low level hook into Mithril's 'onbeforeupdate' method.
    // We use this on to propagate the next Ctx / Props to existing components
    // See: https://mithril.js.org/lifecycle-methods.html#onbeforeupdate
    "onbeforeupdate" to { vnode: dynamic ->

        true.run {
            @Suppress("UnsafeCastFromDynamic")
            (vnode.state[symInstance] as Component<*>).apply {
                _nextCtx(vnode.attrs.ctx)
            }
        }
    },

    // Low level hook into Mithril's 'onbeforeupdate' method.
    // We use this on to propagate the next dom node to the component
    // See: https://mithril.js.org/lifecycle-methods.html#onupdate
    "onupdate" to { vnode: dynamic ->
        true.run {
            @Suppress("UnsafeCastFromDynamic")
            (vnode.state[symInstance] as Component<*>).apply {
                _setDom(vnode.dom)
            }
        }
    },

    // Low level hook into Mithril's 'onremove' method.
    // See: https://mithril.js.org/lifecycle-methods.html#onremove
    "onremove" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*>)._internalOnRemove()
    },

    // Bridging from Mithril's 'view' method to Component.render()
    "view" to { vnode: dynamic ->
        (vnode.state[symInstance] as Component<*>)._internalRender()
    }
).js
