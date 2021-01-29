package de.peekandpoke.kraft.addons.chartjs.demo.barchart

import addons.chartjs.demo.ChartJsDemoData
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag
import kotlinx.html.h2

@Suppress("FunctionName")
fun Tag.ChartJsDemoBarCharts() = comp {
    ChartJsDemoBarCharts(it)
}

@Suppress("FunctionName")
class ChartJsDemoBarCharts(ctx: NoProps) : PureComponent(ctx) {

    class Charts(private val tag: Tag): Tag by tag {

        fun Vertical(data: ChartJsDemoData = ChartJsDemoData().addDatasets(2)) =
            comp(ChartJsDemoBarChartVertical.Props(initial = data)) { ChartJsDemoBarChartVertical(it) }

        fun Horizontal(data: ChartJsDemoData = ChartJsDemoData().addDatasets(2)) =
            comp(ChartJsDemoBarChartHorizontal.Props(initial = data)) { ChartJsDemoBarChartHorizontal(it) }

        fun MultiAxis(data: ChartJsDemoData = ChartJsDemoData().addDatasets(2)) =
            comp(ChartJsDemoBarChartMultiAxis.Props(initial = data)) { ChartJsDemoBarChartMultiAxis(it) }

        fun StackedGroups(data: ChartJsDemoData = ChartJsDemoData().addDatasets(4)) =
            comp(ChartJsDemoBarChartStackedGroups.Props(initial = data)) { ChartJsDemoBarChartStackedGroups(it) }
    }

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        h2 {
            +"ChartJs - Bar Charts"
        }

        Charts(this).apply {
            Vertical()

            Horizontal()

            MultiAxis()

            StackedGroups()
        }
    }
}
