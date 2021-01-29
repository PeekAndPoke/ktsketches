package de.peekandpoke.kraft.addons.chartjs.demo

import de.peekandpoke.kraft.addons.chartjs.demo.barchart.ChartJsDemoBarCharts
import de.peekandpoke.kraft.addons.chartjs.demo.linechart.ChartJsDemoLineCharts
import de.peekandpoke.kraft.addons.routing.RouterBuilder
import de.peekandpoke.kraft.addons.routing.Static

object ChartJsDemoRoutes {

    private const val root = "/demos/chartsjs"

    val Home = Static(root)

    val BarCharts = Static("$root/bar-charts")
    val LineCharts = Static("$root/line-charts")

}

fun RouterBuilder.mountChartJsDemo() {

    mount(ChartJsDemoRoutes.Home) { ChartJsDemos() }

    mount(ChartJsDemoRoutes.BarCharts) { ChartJsDemoBarCharts() }
    mount(ChartJsDemoRoutes.LineCharts) { ChartJsDemoLineCharts() }
}
