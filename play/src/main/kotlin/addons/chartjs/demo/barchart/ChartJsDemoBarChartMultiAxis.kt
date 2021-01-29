package de.peekandpoke.kraft.addons.chartjs.demo.barchart

import addons.chartjs.demo.ChartJsDemoData
import de.peekandpoke.kraft.addons.chartjs.Chart
import de.peekandpoke.kraft.addons.chartjs.ChartJs
import de.peekandpoke.kraft.addons.chartjs.demo.ChartJsDemoDataControls
import de.peekandpoke.kraft.addons.chartjs.demo.ChartJsDemoUtils
import de.peekandpoke.kraft.addons.styling.css
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultra.semanticui.ui
import kotlinx.css.height
import kotlinx.css.vh

@Suppress("DuplicatedCode")
class ChartJsDemoBarChartMultiAxis(ctx: Ctx<Props>) : Component<ChartJsDemoBarChartMultiAxis.Props>(ctx) {

    ////  PROPS  //////////////////////////////////////////////////////////////////////////////////////////////////

    data class Props(
        val initial: ChartJsDemoData
    )

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    private var data by value(props.initial)

    private var stacked by value(false)

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.top.attached.segment {
            ui.header {
                +"Horizontal Bar Chart"
            }
        }

        ui.attached.segment {
            css {
                height = 30.vh
            }

            // Here we pass in all the object used for rendering the chart
            // When any of these are changed the chart is re-rendered
            ChartJs(data, stacked) {

                bar()

                options {
                    title("Title of Multi Axis Bar Chart")

                    responsive()

                    tooltips {
                        index()
                        intersect()
                    }

                    scales {
                        yAxes {
                            display(true)
                            linear()
                            left()
                            id("y-axis-1")
                        }

                        yAxes {
                            display(true)
                            linear()
                            right()
                            id("y-axis-2")
                            gridLines {
                                drawOnChartArea(false)
                            }
                        }
                    }
                }

                data {
                    labels(
                        (0 until data.dataLength).map { ChartJsDemoUtils.month(it) }
                    )

                    data.datasets.forEachIndexed { index, set ->
                        dataset {
                            label(set.label)
                            data(set.values)

                            yAxisID("y-axis-${(index % 2) + 1}")

                            backgroundColor(
                                Chart.helpers.color(set.color).alpha(0.3).rgbString()
                            )
                            borderColor(
                                Chart.helpers.color(set.color).alpha(0.8).rgbString()
                            )
                            borderWidth(1)
                        }
                    }
                }
            }
        }

        ui.bottom.attached.segment {
            ui.form {
                ui.five.fields {
                    ChartJsDemoDataControls(data) { data = it }
                }
            }
        }
    }
}
