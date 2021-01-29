package de.peekandpoke.kraft.addons.chartjs.demo.linechart

import addons.chartjs.demo.ChartJsDemoData
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag
import kotlinx.html.h2

@Suppress("FunctionName")
fun Tag.ChartJsDemoLineCharts() = comp {
    ChartJsDemoLineCharts(it)
}

@Suppress("FunctionName")
class ChartJsDemoLineCharts(ctx: NoProps) : PureComponent(ctx) {

    class Charts(private val tag: Tag) : Tag by tag {

        fun Basic(data: ChartJsDemoData = ChartJsDemoData().addDatasets(2)) =
            comp(ChartJsDemoLineChartBasic.Props(initial = data)) { ChartJsDemoLineChartBasic(it) }

    }

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        h2 {
            +"ChartJs - Line Charts"
        }

        Charts(this).apply {
            Basic()
        }
    }
}
