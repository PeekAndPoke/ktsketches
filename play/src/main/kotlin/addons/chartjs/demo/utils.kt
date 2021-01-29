package de.peekandpoke.kraft.addons.chartjs.demo

import addons.chartjs.demo.ChartJsDemoData
import de.peekandpoke.kraft.addons.chartjs.Chart
import de.peekandpoke.kraft.addons.chartjs.ChartJs
import de.peekandpoke.kraft.addons.styling.css
import de.peekandpoke.ultra.semanticui.ui
import kotlinx.css.height
import kotlinx.css.vh
import kotlinx.html.FlowContent

object ChartJsDemoUtils {

    val chartColors = mapOf(
        "red" to "rgb(255, 99, 132)",
        "orange" to "rgb(255, 159, 64)",
        "yellow" to "rgb(255, 205, 86)",
        "green" to "rgb(75, 192, 192)",
        "blue" to "rgb(54, 162, 235)",
        "purple" to "rgb(153, 102, 255)",
        "grey" to "rgb(201, 203, 207)",
    )

    val chartColorKeys = chartColors.keys.toList()

    fun nextColor(offset: Int): String {
        val rest = offset % chartColorKeys.size

        return chartColors[chartColorKeys[rest]]!!
    }

    var months = listOf(
        "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    )

    fun month(idx: Int) = months[idx % months.size]
}

