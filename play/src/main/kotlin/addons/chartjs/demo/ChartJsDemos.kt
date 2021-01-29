package de.peekandpoke.kraft.addons.chartjs.demo

import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.*

@Suppress("FunctionName")
fun Tag.ChartJsDemos() = comp {
    ChartJsDemos(it)
}

class ChartJsDemos(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {

        h1 { +"ChartJs Demos" }

        ul {
            li {
                a(href = ChartJsDemoRoutes.BarCharts()) { +"Bar Charts" }
            }
            li {
                a(href = ChartJsDemoRoutes.LineCharts()) { +"Line Charts" }
            }
        }
    }
}
