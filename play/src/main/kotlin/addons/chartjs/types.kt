@file:JsModule("chart.js")
@file:JsNonModule
package de.peekandpoke.kraft.addons.chartjs

import org.w3c.dom.CanvasRenderingContext2D

external class Chart(
    ctx: CanvasRenderingContext2D,
    data: dynamic,
) {
    fun update()

    var data: dynamic
    var options: dynamic

    @Suppress("ClassName")
    object helpers {
        fun configMerge(val1: dynamic, val2: dynamic)

        fun color(color: dynamic): Color

        class Color {
            fun alpha(alpha: Number): Color
            fun rgbString(): String
        }
    }
}
