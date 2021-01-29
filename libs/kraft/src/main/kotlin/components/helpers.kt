package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.vdom.VDomTagConsumer
import kotlinx.html.CommonAttributeGroupFacade
import kotlinx.html.FlowContent
import kotlinx.html.Tag

typealias RenderFn = FlowContent.() -> Unit

/**
 * Adds a child component to the current tag
 */
fun <P> Tag.comp(props: P, component: (Ctx<P>) -> Component<P>) =
    (this.consumer as VDomTagConsumer).onComponent(props, component)

/**
 * Adds a parameterless child component to the current tag
 */
fun Tag.comp(component: (NoProps) -> Component<Nothing?>) = comp(null, component)

/**
 * Markup Element key
 */
var CommonAttributeGroupFacade.key: String
    get() = attributes["key"] ?: ""
    set(value) {
        attributes["key"] = value
    }

