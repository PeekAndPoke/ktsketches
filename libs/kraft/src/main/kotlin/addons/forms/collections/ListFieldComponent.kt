package de.peekandpoke.kraft.addons.forms.collections

import de.peekandpoke.kraft.addons.utils.modifyAt
import de.peekandpoke.kraft.addons.utils.removeAt
import de.peekandpoke.kraft.addons.utils.swap
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.FlowContent
import kotlinx.html.Tag


@Suppress("FunctionName")
fun <T> Tag.ListField(
    items: List<T>,
    onChange: (List<T>) -> Unit,
    customize: ListFieldComponent.PropsBuilder<T>.() -> Unit
) = comp(ListFieldComponent.PropsBuilder(items, onChange).apply(customize).build()) {
    ListFieldComponent(it)
}

class ListFieldComponent<T>(ctx: Ctx<Props<T>>) : Component<ListFieldComponent.Props<T>>(ctx) {

    data class Props<T>(
        val items: List<T>,
        val onChange: (List<T>) -> Unit,
        val renderItem: FlowContent.(ItemCtx<T>) -> Unit,
        val renderAdd: FlowContent.(AddCtx<T>) -> Unit
    )

    class PropsBuilder<T>(
        val items: List<T>,
        val onChange: (List<T>) -> Unit
    ) {
        private var renderItem: FlowContent.(ItemCtx<T>) -> Unit = {}
        private var renderAdd: FlowContent.(AddCtx<T>) -> Unit = {}

        fun build() = Props(items, onChange, renderItem, renderAdd)

        fun renderItem(block: FlowContent.(ItemCtx<T>) -> Unit) {
            renderItem = block
        }

        fun renderAdd(block: FlowContent.(AddCtx<T>) -> Unit) {
            renderAdd = block
        }
    }

    data class ItemCtx<T>(
        val idx: Int,
        val item: T,
        val all: List<T>,
        val modify: (T) -> Unit,
        val swapWith: (idx: Int) -> Unit,
        val remove: () -> Unit
    )

    data class AddCtx<T>(
        val add: (T) -> Unit
    )

    ////  IMPL  ////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        props.apply {

            items.forEachIndexed { idx, item ->
                val ctx = ItemCtx(
                    idx = idx,
                    item = item,
                    all = props.items,
                    modify = { new -> onChange(items.modifyAt(idx) { new }) },
                    swapWith = { other -> onChange(items.swap(idx, other)) },
                    remove = { onChange(items.removeAt(idx)) }
                )

                props.renderItem(this@render, ctx)
            }

            val ctx = AddCtx { new: T -> onChange(items.plus(new)) }

            props.renderAdd(this@render, ctx)
        }
    }
}
