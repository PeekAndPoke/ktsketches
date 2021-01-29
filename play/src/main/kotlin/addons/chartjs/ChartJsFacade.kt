package de.peekandpoke.kraft.addons.chartjs

import de.peekandpoke.kraft.components.comp
import kotlinx.html.Tag

val Tag.ChartJs get() = ChartJsFacade(this)

@Suppress("FunctionName", "MemberVisibilityCanBePrivate")
class ChartJsFacade(private val tag: Tag) {

    operator fun invoke(
        vararg data: Any?,
        compose: ChartJs.Builder.() -> Obj
    ) = Raw(
        data = data.toList(),
        compose = { ChartJs.Builder().apply { compose() }.toJs() }
    )

    fun Raw(vararg data: Any?, compose: () -> dynamic) = Raw(data = data.toList(), compose = compose)

    fun Raw(data: List<Any?>, compose: () -> dynamic) = tag.comp(
        ChartJsComponent.Props(data = data, compose = compose)
    ) {
        ChartJsComponent(it)
    }
}
