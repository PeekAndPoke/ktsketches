package de.peekandpoke.kraft.vdom.mithril

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.vdom.VDomElement
import de.peekandpoke.kraft.vdom.VDomTagConsumer
import kotlinx.html.Entities
import kotlinx.html.Tag
import kotlinx.html.Unsafe
import org.w3c.dom.events.Event

/**
 * Implementation of [VDomTagConsumer] for the mithril engine
 *
 * see: https://mithril.js.org
 */
class MithrilTagConsumer(
    private val engine: MithrilVDomEngine,
    private val host: Component<*>?
) : VDomTagConsumer {

    /** The root element */
    private val root = MithrilRootElement()

    /** The stack of elements we are visiting */
    private val stack = mutableListOf<VDomElement>(
        root
    )

    override fun <P> onComponent(params: P, component: (Ctx<P>) -> Component<P>) {
//        console.log(component)

        stack.last().appendChild(
            MithrilComponentElement(
                Ctx(engine, host, params),
                component
            )
        )
    }

    override fun finalize(): VDomElement {
//        console.log("finalize")

        return stack.first()
    }

    override fun onTagAttributeChange(tag: Tag, attribute: String, value: String?) {
//        console.log("onTagAttributeChange", tag, attribute, value)
    }

    override fun onTagComment(content: CharSequence) {
        console.log("onTagComment", content)
    }

    override fun onTagContent(content: CharSequence) {
//        console.log("onTagContent", content)

        stack.last().appendChild(
            MithrilContentElement(content)
        )
    }

    override fun onTagContentEntity(entity: Entities) {
//        console.log("onTagContentEntity", entity)
    }

    override fun onTagContentUnsafe(block: Unsafe.() -> Unit) {
        stack.last().appendChild(
            MithrilUnsafeContentElement().apply(block)
        )
    }

    override fun onTagEnd(tag: Tag) {
//        console.log("onTagEnd", tag)

        stack.removeAt(stack.size - 1)
    }

    override fun onTagEvent(tag: Tag, event: String, value: (Event) -> Unit) {
//        console.log("onTagEvent", tag, event, value)

        stack.last().addEvent(event, value)
    }

    override fun onTagStart(tag: Tag) {
//        console.log("onTagStart", tag)

        val element = MithrilTagElement(
            tag = tag
        )

        stack.last().appendChild(element)

        stack.add(element)
    }
}
