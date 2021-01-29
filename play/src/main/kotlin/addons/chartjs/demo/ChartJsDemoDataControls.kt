package de.peekandpoke.kraft.addons.chartjs.demo

import addons.chartjs.demo.ChartJsDemoData
import de.peekandpoke.kraft.components.Component
import de.peekandpoke.kraft.components.Ctx
import de.peekandpoke.kraft.components.comp
import de.peekandpoke.kraft.components.onClick
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultra.semanticui.ui
import kotlinx.html.Tag

@Suppress("FunctionName")
fun Tag.ChartJsDemoDataControls(
    data: ChartJsDemoData,
    onChange: (ChartJsDemoData) -> Unit
) = comp(
    ChartJsDemoDataControls.Props(data = data, onChange = onChange)
) {
    ChartJsDemoDataControls(it)
}

class ChartJsDemoDataControls(ctx: Ctx<Props>) : Component<ChartJsDemoDataControls.Props>(ctx) {

    ////  PROPS  //////////////////////////////////////////////////////////////////////////////////////////////////

    data class Props(
        val data: ChartJsDemoData,
        val onChange: (ChartJsDemoData) -> Unit
    )

    ////  STATE  //////////////////////////////////////////////////////////////////////////////////////////////////

    ////  IMPL  ///////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.field {
            ui.fluid.basic.button {
                +"Randomize"
                onClick {
                    props.onChange(props.data.randomize())
                }
            }
        }

        ui.field {
            ui.fluid.basic.button {
                +"Add Dataset"
                onClick {
                    props.onChange(props.data.addDatasets())
                }
            }
        }

        ui.field {
            ui.fluid.basic.button {
                +"Drop Dataset"
                onClick {
                    props.onChange(props.data.dropLastDatasets())
                }
            }
        }

        ui.field {
            ui.fluid.basic.button {
                +"Add Data point"
                onClick {
                    props.onChange(props.data.incDataLength())
                }
            }
        }

        ui.field {
            ui.fluid.basic.button {
                +"Remove Data point"
                onClick {
                    props.onChange(props.data.decDataLength())
                }
            }
        }
    }
}
