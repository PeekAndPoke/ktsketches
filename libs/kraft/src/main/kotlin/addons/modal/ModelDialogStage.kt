package de.peekandpoke.kraft.addons.modal

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.ModelDialogStage(
    modals: ModalDialogs
) = comp(
    ModelDialogStage.Props(modals = modals)
) {
    ModelDialogStage(it)
}

class ModelDialogStage(ctx: Ctx<Props>) : Component<ModelDialogStage.Props>(ctx) {

    ////  PROPS  //////////////////////////////////////////////////////////////////////////////////////////////////

    data class Props(
        val modals: ModalDialogs,
    )

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    private val current by subscribingTo(props.modals.current)

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        current?.let {
            it.view(this, it)
        }
    }
}
