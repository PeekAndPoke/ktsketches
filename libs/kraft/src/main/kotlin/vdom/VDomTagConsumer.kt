package de.peekandpoke.kraft.vdom

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import kotlinx.html.TagConsumer

interface VDomTagConsumer : TagConsumer<VDomElement> {

    fun <P> onComponent(params: P, component: (Ctx<P>) -> Component<P>)
}
