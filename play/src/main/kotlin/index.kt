package de.peekandpoke.kraft

import de.peekandpoke.kraft.addons.chartjs.demo.ChartJsDemos
import de.peekandpoke.kraft.addons.chartjs.demo.mountChartJsDemo
import de.peekandpoke.kraft.addons.routing.RouterComponent
import de.peekandpoke.kraft.addons.routing.Static
import de.peekandpoke.kraft.addons.routing.router
import de.peekandpoke.kraft.vdom.mithril.MithrilVDomEngine
import de.peekandpoke.ultra.semanticui.ui
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

val MainRouter = router {

    mount(Static("")) { ChartJsDemos() }

    mountChartJsDemo()
}


fun main() {
    document.addEventListener("DOMContentLoaded", { launch() })
}

private fun launch() {
    val mountPoint = document.getElementById("spa") as HTMLElement

    MithrilVDomEngine().mount(mountPoint) {
        ui.container {
            RouterComponent(MainRouter)
        }
    }

    MainRouter.resolveCurrentRoute()
}

