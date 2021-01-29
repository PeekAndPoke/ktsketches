package de.peekandpoke.kraft.addons.chartjs

import de.peekandpoke.ultra.jshelpers.jsArray
import de.peekandpoke.ultra.jshelpers.jsObject

val Boolean.chartJs get() = RawObj(this)
val Iterable<Boolean>.chartJs get() = ListObj(this.map { it.chartJs })
val Array<out Boolean>.chartJs get() = this.toList().chartJs

val Number.chartJs get() = RawObj(this)
val Iterable<Number>.chartJs get() = ListObj(this.map { it.chartJs })
val Array<out Number>.chartJs get() = this.toList().chartJs

val String.chartJs get() = RawObj(this)
val Iterable<String>.chartJs get() = ListObj(this.map { it.chartJs })
val Array<out String>.chartJs get() = this.toList().chartJs

interface Obj {
    fun toJs(): dynamic
}


class RawObj(private val data: Any?) : Obj {
    override fun toJs(): dynamic = data
}

class ListObj(initial: List<Obj> = emptyList()) : Obj {

    private val entries = mutableListOf<Obj>().apply { addAll(initial) }

    fun add(entry: Obj) {
        entries.add(entry)
    }

    override fun toJs(): dynamic {
        val result = jsArray

        @Suppress("UnsafeCastFromDynamic")
        entries.forEach { result.push(it.toJs()) }

        return result
    }
}

open class KeyValueObj(initial: Map<String, Obj> = emptyMap()) : Obj {

    val entries = mutableMapOf<String, Obj>().apply { putAll(initial) }

    fun <T : Obj> set(key: String, value: T): T = value.also { entries[key] = it }

    fun set(key: String, value: Boolean) = set(key, value.chartJs)
    fun set(key: String, value: List<Boolean>) = set(key, value.chartJs)
    fun set(key: String, value: Array<out Boolean>) = set(key, value.chartJs)

    fun set(key: String, value: Number) = set(key, value.chartJs)
    fun set(key: String, value: List<Number>) = set(key, value.chartJs)
    fun set(key: String, value: Array<out Number>) = set(key, value.chartJs)

    fun set(key: String, value: String) = set(key, value.chartJs)
    fun set(key: String, value: List<String>) = set(key, value.chartJs)
    fun set(key: String, value: Array<out String>) = set(key, value.chartJs)

    fun set(vararg pairs: Pair<String, Any?>) {
        pairs.forEach {
            set(it.first, RawObj(it.second))
        }
    }

    fun getOrNull(key: String): Obj? = entries[key]

    fun get(key: String): Obj = getOrNull(key)!!

    @Suppress("UNCHECKED_CAST")
    fun <T : Obj> get(key: String): T = entries[key] as T

    inline fun <reified T : Obj> getOrPut(key: String, default: () -> T): T =
        when (val entry = entries[key]) {
            is T -> entry
            else -> default().also { set(key, it) }
        }

    override fun toJs(): dynamic {
        val result = jsObject

        entries.forEach {
            result[it.key] = it.value.toJs()
        }

        return result
    }
}

fun chartJs(block: ChartJs.Builder.() -> Unit) = ChartJs.Builder().apply(block)

@Suppress("EnumEntryName")
interface ChartJs {

    @Suppress("EnumEntryName")
    enum class Type {
        doughnut,
        pie,
        line,
        bar,
        horizontalBar,
        radar,
        polarArea,
        bubble,
        scatter,
    }

    /**
     * See https://www.chartjs.org/docs/latest/general/interactions/modes.html#interaction-modes
     */
    enum class InteractionMode {
        point,
        nearest,
        index,
        dataset,
        x,
        y,
    }

    enum class Position {
        top,
        left,
        bottom,
        right
    }

    interface WithChartType {

        fun <T : Obj> set(key: String, value: T): T

        fun type(type: Type) = set("type", type.toString().chartJs)

        fun doughnut() = type(Type.doughnut)
        fun pie() = type(Type.pie)
        fun line() = type(Type.line)
        fun bar() = type(Type.bar)
        fun horizontalBar() = type(Type.horizontalBar)
        fun radar() = type(Type.radar)
        fun polarArea() = type(Type.polarArea)
        fun bubble() = type(Type.bubble)
        fun scatter() = type(Type.scatter)
    }

    class Builder : KeyValueObj(), WithChartType {

        class Options : KeyValueObj() {

            class Scales : KeyValueObj() {

                class ScaleConfig : KeyValueObj() {

                    class GridLines: KeyValueObj() {
                        fun drawOnChartArea(drawOnChartArea: Boolean) = set("drawOnChartArea", drawOnChartArea)
                    }

                    fun id(id: String) = set("id", id)

                    fun display(display: Boolean = true) = set("display", display)
                    fun displayAuto() = set("display", "auto")

                    fun gridLines(block: GridLines.() -> Unit) = set("gridLines", GridLines().apply(block))

                    fun weight(weight: Number) = set("weight", weight)

                    fun position(position: Position) = set("position", position.toString())
                    fun top() = position(Position.top)
                    fun left() = position(Position.left)
                    fun bottom() = position(Position.bottom)
                    fun right() = position(Position.right)

                    fun type(type: String) = set("type", type)
                    fun linear() = type("linear")
                    fun logarithmic() = type("logarithmic")
                    fun category() = type("category")
                    fun time() = type("time")

                    fun stacked(stacked: Boolean = true) = set("stacked", stacked)
                }

                fun xAxes(block: ScaleConfig.() -> Unit) =
                    getOrPut("xAxes") { ListObj() }.apply { add(ScaleConfig().apply(block)) }

                fun yAxes(block: ScaleConfig.() -> Unit) =
                    getOrPut("yAxes") { ListObj() }.apply { add(ScaleConfig().apply(block)) }
            }

            class Tooltips: KeyValueObj() {
                // TODO: see https://www.chartjs.org/docs/latest/configuration/tooltip.html
                fun mode(mode: InteractionMode) = set("mode", mode.toString())

                fun point() = mode(InteractionMode.point)
                fun nearest() = mode(InteractionMode.nearest)
                fun index() = mode(InteractionMode.index)
                fun index(axis: String) {
                    mode(InteractionMode.index)
                    axis(axis)
                }
                fun dataset() = mode(InteractionMode.dataset)
                fun x() = mode(InteractionMode.x)
                fun y() = mode(InteractionMode.y)

                fun axis(axis: String) = set("axis", axis)

                fun intersect(intersect: Boolean = true) = set("intersect", intersect)
            }

            fun title(title: String) = set("title", KeyValueObj()).apply {
                set("display", true)
                set("text", title)
            }

            fun responsive(responsive: Boolean = true) = set("responsive", responsive)

            fun scales(block: Scales.() -> Unit) = set("scales", Scales().apply(block))

            fun tooltips(block: Tooltips.() -> Unit) = set("tooltips", Tooltips().apply(block))
        }

        //// Setting the "options" property
        fun options(block: Options.() -> Unit) = set("options", Options().apply(block))

        //// Setting the "data" property
        fun data(block: Data.() -> Unit) = set("data", Data().apply(block))
    }


    class Data : KeyValueObj() {

        init {
            set("datasets", ListObj())
        }

        fun labels(vararg label: String) = labels(label.toList())
        fun labels(labels: List<String>) = set("labels", labels)

        fun dataset(block: DataSet.() -> Unit) = getOrPut("datasets") { ListObj() }.add(DataSet().apply(block))
    }

    class DataSet : KeyValueObj(), WithChartType {

        fun label(label: String) = set("label", label)

        fun data(vararg item: Number) = data(item.toList())
        fun data(data: List<Number>) = set("data", data)

        fun backgroundColor(color: String) = set("backgroundColor", color)
        fun backgroundColor(color: List<String>) = set("backgroundColor", color)
        fun backgroundColor(vararg color: String) = set("backgroundColor", color)

        fun borderColor(color: String) = set("borderColor", color)
        fun borderColor(color: List<String>) = set("borderColor", color)
        fun borderColor(color: Array<out String>) = set("borderColor", color)

        fun borderWidth(borderWidth: Int) = set("borderWidth", borderWidth)

        fun fill(fill: Boolean) = set("fill", fill)

        fun yAxisID(yAxisID: String) = set("yAxisID", yAxisID)

        fun stack(stackId: String) = set("stack", stackId)
    }
}

