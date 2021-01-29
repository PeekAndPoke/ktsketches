package de.peekandpoke.kraft


import de.peekandpoke.kraft.addons.chartjs.demo.barchart.ChartJsDemoBarCharts
import de.peekandpoke.kraft.addons.routing.RouterComponent
import de.peekandpoke.kraft.components.NoProps
import de.peekandpoke.kraft.components.PureComponent
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.vdom.VDom
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.TestComp() = comp {
    TestComp(it)
}

class TestComp(ctx: NoProps) : PureComponent(ctx) {

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        RouterComponent(MainRouter)
    }
}
