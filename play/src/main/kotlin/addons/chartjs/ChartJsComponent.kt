package de.peekandpoke.kraft.addons.chartjs

import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultra.jshelpers.Object
import de.peekandpoke.ultra.jshelpers.jsArray
import kotlinx.html.canvas
import kotlinx.html.style
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement

class ChartJsComponent(ctx: Ctx<Props>) : Component<ChartJsComponent.Props>(ctx) {

    ////  PROPS  //////////////////////////////////////////////////////////////////////////////////////////////////

    data class Props(
        val data: List<Any?>,
        val compose: () -> dynamic
    )

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    private var chart: Chart? = null

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCreate() {
        super.onCreate()

        try {
            val canvas = dom as HTMLCanvasElement
            val context = canvas.getContext("2d") as CanvasRenderingContext2D
            val data = props.compose()

            console.log(data)

            chart = Chart(ctx = context, data = data)
        } catch (e: Throwable) {
            console.error(e)
        }
    }

    override fun shouldRedraw(nextProps: Props): Boolean {
        return false.also {

            if (props.data != nextProps.data) {

                val chartInst = chart ?: return@also

                val componentData = chartInst.data
                val componentDatasets = componentData.datasets

                val next = nextProps.compose()
                val nextData = next.data
                val nextDatasets = nextData.datasets ?: jsArray

                ////  Merge options  ///////////////////////////////////////////////////////////////////////////////////

                if (next.options) {
                    chartInst.options = Chart.helpers.configMerge(chartInst.options, next.options)
                }

                ////  Update Labels  ///////////////////////////////////////////////////////////////////////////////////

                componentData.labels = nextData.labels

                ////  Datasets  ////////////////////////////////////////////////////////////////////////////////////////
                // drop datasets the have been remove
                while (componentDatasets.length > nextDatasets.length) {
                    componentDatasets.pop()
                }

                nextDatasets.forEach { dataset, idx ->

                    if (idx < componentDatasets.length) {
                        // update existing datasets
                        Object.keys(dataset).forEach { key -> componentDatasets[idx][key] = dataset[key] }
                    } else {
                        // add new datasets
                        componentDatasets.push(dataset)
                    }
                }

                // Trigger the redraw of the chart
                chart!!.update()
            }

        }
    }

    override fun VDom.render() {
        canvas {
            style = "width: 100%; height: 100%;"
        }
    }
}
