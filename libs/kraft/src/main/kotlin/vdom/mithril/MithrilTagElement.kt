package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.kraft.vdom.VDomElement
import de.peekandpoke.ultra.jshelpers.js
import de.peekandpoke.ultra.jshelpers.jsArray
import kotlinx.html.Tag
import org.w3c.dom.events.Event

data class MithrilTagElement(
    val tag: Tag,
    val children: MutableList<VDomElement> = mutableListOf(),
    val events: MutableMap<String, (Event) -> Any?> = mutableMapOf()
) : VDomElement {

    override fun appendChild(child: VDomElement) {
        children.add(child)
    }

    override fun addEvent(name: String, callback: (Event) -> Any?) {
        events[name] = callback
    }

    override fun render(): dynamic {
        // Convert attributes to plain js object
        val attrs = tag.attributes.js
        // Merge the events into the attributes
        events.forEach { (k, v) -> attrs[k] = v }

        // Create a low level array for the render results of the children
        val childArr = jsArray
        // Render each child and add it to the array
        for (child in children) {
            @Suppress("UnsafeCastFromDynamic")
            childArr.push(child.render())
        }

        // Render with mithril
        return m(tag.tagName, attrs, childArr)
    }
}
