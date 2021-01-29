package de.peekandpoke.kraft.addons.chartjs.demo.barchart

import addons.chartjs.demo.ChartJsDemoData
import de.peekandpoke.kraft.addons.chartjs.Chart
import de.peekandpoke.kraft.addons.chartjs.ChartJs
import de.peekandpoke.kraft.addons.chartjs.demo.ChartJsDemoDataControls
import de.peekandpoke.kraft.addons.chartjs.demo.ChartJsDemoUtils
import de.peekandpoke.kraft.addons.semanticui.forms.input.CheckboxField
import de.peekandpoke.kraft.addons.styling.css
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultra.semanticui.ui
import kotlinx.css.height
import kotlinx.css.vh

@Suppress("DuplicatedCode")
class ChartJsDemoBarChartHorizontal(ctx: Ctx<Props>) : Component<ChartJsDemoBarChartHorizontal.Props>(ctx) {

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

                horizontalBar()

                options {
                    title("Title of Horizontal Bar Chart")

                    scales {
                        xAxes {
                            stacked(stacked)
                        }
                        yAxes {
                            stacked(stacked)
                        }
                    }
                }

                data {
                    labels(
                        (0 until data.dataLength).map { ChartJsDemoUtils.month(it) }
                    )

                    data.datasets.forEach { set ->
                        dataset {
                            label(set.label)
                            data(set.values)

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

                ui.five.fields {
                    ui.field {
                        CheckboxField(::stacked) {
                            label("Stacked")
                            slider()
                        }
                    }
                }
            }
        }
    }
}
