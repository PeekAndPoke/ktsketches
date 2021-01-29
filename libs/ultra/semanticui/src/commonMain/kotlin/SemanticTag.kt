package de.peekandpoke.ultra.semanticui

import kotlinx.html.*

@Suppress("FunctionName", "PropertyName", "unused")
class SemanticTag(val __parent: FlowContent, val __cssClasses: MutableList<String>) {

    // Rendering DIV implicitly as the default

    @SemanticUiCssMarker
    operator fun invoke(flow: DIV.() -> Unit) = Div(flow)

    // Rendering Tags explicitly

    @SemanticUiTagMarker infix fun H1(flow: H1.() -> Unit) =
        __parent.h1(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun H2(flow: H2.() -> Unit) =
        __parent.h2(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun H3(flow: H3.() -> Unit) =
        __parent.h3(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun H4(flow: H4.() -> Unit) =
        __parent.h4(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun H5(flow: H5.() -> Unit) =
        __parent.h5(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun H6(flow: H6.() -> Unit) =
        __parent.h6(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun A(flow: A.() -> Unit) =
        __parent.a(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun B(flow: B.() -> Unit) =
        __parent.b(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Button(flow: BUTTON.() -> Unit) =
        __parent.button(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Div(flow: DIV.() -> Unit) =
        __parent.div(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Form(flow: FORM.() -> Unit) =
        __parent.form(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun I(flow: I.() -> Unit) =
        __parent.i(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Label(flow: LABEL.() -> Unit) =
        __parent.label(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun P(flow: P.() -> Unit) =
        __parent.p(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Span(flow: SPAN.() -> Unit) =
        __parent.span(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Submit(flow: BUTTON.() -> Unit) =
        __parent.button(classes = __cssClasses.joinToString(" "), block = flow)

    @SemanticUiTagMarker infix fun Table(flow: TABLE.() -> Unit) =
        __parent.table(classes = __cssClasses.joinToString(" "), block = flow)

    // Adding Custom Css Classes

    @SemanticUiCssMarker fun with(block: SemanticTag.() -> SemanticTag): SemanticTag = this.run(block)

    @SemanticUiCssMarker fun with(vararg cls: String) = this + cls

    @SemanticUiCssMarker fun with(vararg cls: String, flow: DIV.() -> Unit) = (this + cls).invoke(flow)


    operator fun plus(cls: String) = apply { __cssClasses.add(cls) }

    operator fun plus(classes: Array<out String>) = apply { __cssClasses.addAll(classes) }


    // Conditional classes

    @SemanticUiConditionalMarker fun given(condition: Boolean, action: SemanticTag.() -> SemanticTag) =
        when (condition) {
            false -> this
            else -> this.action()
        }

    @SemanticUiConditionalMarker val then get() = this

    // SemanticUI Numbers

    @SemanticUiCssMarker fun number(int: Int) = number(SemanticNumber.of(int))
    @SemanticUiCssMarker fun number(number: SemanticNumber) = this + number.toString()

    @SemanticUiCssMarker val one get() = this + "one"
    @SemanticUiCssMarker val two get() = this + "two"
    @SemanticUiCssMarker val three get() = this + "three"
    @SemanticUiCssMarker val four get() = this + "four"
    @SemanticUiCssMarker val five get() = this + "five"
    @SemanticUiCssMarker val six get() = this + "six"
    @SemanticUiCssMarker val seven get() = this + "seven"
    @SemanticUiCssMarker val eight get() = this + "eight"
    @SemanticUiCssMarker val nine get() = this + "nine"
    @SemanticUiCssMarker val ten get() = this + "ten"
    @SemanticUiCssMarker val eleven get() = this + "eleven"
    @SemanticUiCssMarker val twelve get() = this + "twelve"
    @SemanticUiCssMarker val thirteen get() = this + "thirteen"
    @SemanticUiCssMarker val fourteen get() = this + "fourteen"
    @SemanticUiCssMarker val fifteen get() = this + "fifteen"
    @SemanticUiCssMarker val sixteen get() = this + "sixteen"

    // SemanticUI Colors

    @SemanticUiCssMarker fun color(color: SemanticColor) = when {
        color.isSet -> with(color.toString())
        else -> this
    }

    @SemanticUiCssMarker fun color(color: SemanticColor, flow: DIV.() -> Unit) = with(color.toString(), flow = flow)

    @SemanticUiCssMarker val color get() = this + "color"
    @SemanticUiCssMarker val inverted get() = this + "inverted"

    @SemanticUiCssMarker val red get() = this + "red"
    @SemanticUiCssMarker val orange get() = this + "orange"
    @SemanticUiCssMarker val yellow get() = this + "yellow"
    @SemanticUiCssMarker val olive get() = this + "olive"
    @SemanticUiCssMarker val green get() = this + "green"
    @SemanticUiCssMarker val teal get() = this + "teal"
    @SemanticUiCssMarker val blue get() = this + "blue"
    @SemanticUiCssMarker val violet get() = this + "violet"
    @SemanticUiCssMarker val purple get() = this + "purple"
    @SemanticUiCssMarker val pink get() = this + "pink"
    @SemanticUiCssMarker val brown get() = this + "brown"
    @SemanticUiCssMarker val grey get() = this + "grey"
    @SemanticUiCssMarker val black get() = this + "black"
    @SemanticUiCssMarker val white get() = this + "white"

    // SemanticUI Sizes

    @SemanticUiCssMarker val short get() = this + "short"
    @SemanticUiCssMarker val long get() = this + "long"

    @SemanticUiCssMarker val mini get() = this + "mini"
    @SemanticUiCssMarker val tiny get() = this + "tiny"
    @SemanticUiCssMarker val small get() = this + "small"
    @SemanticUiCssMarker val medium get() = this + "medium"
    @SemanticUiCssMarker val large get() = this + "large"
    @SemanticUiCssMarker val big get() = this + "big"
    @SemanticUiCssMarker val huge get() = this + "huge"
    @SemanticUiCssMarker val massive get() = this + "massive"

    // SemanticUI Emphasis

    @SemanticUiCssMarker val primary get() = this + "primary"
    @SemanticUiCssMarker val secondary get() = this + "secondary"
    @SemanticUiCssMarker val positive get() = this + "positive"
    @SemanticUiCssMarker val negative get() = this + "negative"
    @SemanticUiCssMarker val warning get() = this + "warning"

    // SemanticUI Brands

    @SemanticUiCssMarker val facebook get() = this + "facebook"
    @SemanticUiCssMarker val twitter get() = this + "twitter"
    @SemanticUiCssMarker val google_plus get() = this + "google plus"
    @SemanticUiCssMarker val linkedin get() = this + "linkedin"
    @SemanticUiCssMarker val instagram get() = this + "instagram"
    @SemanticUiCssMarker val youtube get() = this + "youtube"

    // Semantic UI Words

    @SemanticUiCssMarker val text get() = this + "text"
    @SemanticUiCssMarker val actions get() = this + "actions"
    @SemanticUiCssMarker val active get() = this + "active"
    @SemanticUiCssMarker val basic get() = this + "basic"
    @SemanticUiCssMarker val bar get() = this + "bar"
    @SemanticUiCssMarker val button get() = this + "button"
    @SemanticUiCssMarker val buttons get() = this + "buttons"
    @SemanticUiCssMarker val circular get() = this + "circular"
    @SemanticUiCssMarker val compact get() = this + "compact"
    @SemanticUiCssMarker val disabled get() = this + "disabled"
    @SemanticUiCssMarker val divider get() = this + "divider"
    @SemanticUiCssMarker val dividing get() = this + "dividing"
    @SemanticUiCssMarker val down get() = this + "down"
    @SemanticUiCssMarker val floated get() = this + "floated"
    @SemanticUiCssMarker val fluid get() = this + "fluid"
    @SemanticUiCssMarker val piled get() = this + "piled"
    @SemanticUiCssMarker val fixed get() = this + "fixed"
    @SemanticUiCssMarker val header get() = this + "header"
    @SemanticUiCssMarker val icon get() = this + "icon"
    @SemanticUiCssMarker val image get() = this + "image"
    @SemanticUiCssMarker val line get() = this + "line"
    @SemanticUiCssMarker val link get() = this + "link"
    @SemanticUiCssMarker val list get() = this + "list"
    @SemanticUiCssMarker val loading get() = this + "loading"
    @SemanticUiCssMarker val message get() = this + "message"
    @SemanticUiCssMarker val overlay get() = this + "overlay"
    @SemanticUiCssMarker val pointing get() = this + "pointing"
    @SemanticUiCssMarker val scale get() = this + "scale"
    @SemanticUiCssMarker val shrink get() = this + "shrink"
    @SemanticUiCssMarker val toggle get() = this + "toggle"
    @SemanticUiCssMarker val styled get() = this + "styled"
    @SemanticUiCssMarker val accordion get() = this + "accordion"
    @SemanticUiCssMarker val title get() = this + "title"
    @SemanticUiCssMarker val description get() = this + "description"
    @SemanticUiCssMarker val transition get() = this + "transition"
    @SemanticUiCssMarker val relaxed get() = this + "relaxed"
    @SemanticUiCssMarker val attached get() = this + "attached"
    @SemanticUiCssMarker val top get() = this + "top"
    @SemanticUiCssMarker val bottom get() = this + "bottom"
    @SemanticUiCssMarker val placeholder get() = this + "placeholder"
    @SemanticUiCssMarker val cards get() = this + "cards"
    @SemanticUiCssMarker val card get() = this + "card"
    @SemanticUiCssMarker val raised get() = this + "raised"
    @SemanticUiCssMarker val meta get() = this + "meta"
    @SemanticUiCssMarker val dropdown get() = this + "dropdown"
    @SemanticUiCssMarker val sticky get() = this + "sticky"
    @SemanticUiCssMarker val inline get() = this + "inline"
    @SemanticUiCssMarker val progress get() = this + "progress"
    @SemanticUiCssMarker val paragraph get() = this + "paragraph"
    @SemanticUiCssMarker val slider get() = this + "slider"
    @SemanticUiCssMarker val success get() = this + "success"
    @SemanticUiCssMarker val ordered get() = this + "ordered"
    @SemanticUiCssMarker val steps get() = this + "steps"
    @SemanticUiCssMarker val step get() = this + "step"
    @SemanticUiCssMarker val completed get() = this + "completed"
    @SemanticUiCssMarker val computer get() = this + "computer"
    @SemanticUiCssMarker val tablet get() = this + "tablet"
    @SemanticUiCssMarker val mobile get() = this + "mobile"
    @SemanticUiCssMarker val form get() = this + "form"
    @SemanticUiCssMarker val field get() = this + "field"
    @SemanticUiCssMarker val fields get() = this + "fields"
    @SemanticUiCssMarker val checkbox get() = this + "checkbox"
    @SemanticUiCssMarker val error get() = this + "error"
    @SemanticUiCssMarker val input get() = this + "input"
    @SemanticUiCssMarker val sortable get() = this + "sortable"
    @SemanticUiCssMarker val table get() = this + "table"
    @SemanticUiCssMarker val container get() = this + "container"
    @SemanticUiCssMarker val segment get() = this + "segment"
    @SemanticUiCssMarker val segments get() = this + "segments"
    @SemanticUiCssMarker val pusher get() = this + "pusher"
    @SemanticUiCssMarker val left get() = this + "left"
    @SemanticUiCssMarker val right get() = this + "right"
    @SemanticUiCssMarker val horizontal get() = this + "horizontal"
    @SemanticUiCssMarker val vertical get() = this + "vertical"
    @SemanticUiCssMarker val row get() = this + "row"
    @SemanticUiCssMarker val column get() = this + "column"
    @SemanticUiCssMarker val grid get() = this + "grid"
    @SemanticUiCssMarker val wide get() = this + "wide"
    @SemanticUiCssMarker val stackable get() = this + "stackable"
    @SemanticUiCssMarker val stretched get() = this + "stretched"
    @SemanticUiCssMarker val aligned get() = this + "aligned"
    @SemanticUiCssMarker val center get() = this + "center"
    @SemanticUiCssMarker val centered get() = this + "centered"
    @SemanticUiCssMarker val divided get() = this + "divided"
    @SemanticUiCssMarker val celled get() = this + "celled"
    @SemanticUiCssMarker val middle get() = this + "middle"
    @SemanticUiCssMarker val padded get() = this + "padded"
    @SemanticUiCssMarker val animated get() = this + "animated"
    @SemanticUiCssMarker val animating get() = this + "animating"
    @SemanticUiCssMarker val fade get() = this + "fade"
    @SemanticUiCssMarker val out get() = this + "out"
    @SemanticUiCssMarker val _in get() = this + "in"
    @SemanticUiCssMarker val extra get() = this + "extra"
    @SemanticUiCssMarker val content get() = this + "content"
    @SemanticUiCssMarker val scrolling get() = this + "scrolling"
    @SemanticUiCssMarker val visible get() = this + "visible"
    @SemanticUiCssMarker val hidden get() = this + "hidden"
    @SemanticUiCssMarker val label get() = this + "label"
    @SemanticUiCssMarker val tag get() = this + "tag"
    @SemanticUiCssMarker val labeled get() = this + "labeled"
    @SemanticUiCssMarker val menu get() = this + "menu"
    @SemanticUiCssMarker val tabular get() = this + "tabular"
    @SemanticUiCssMarker val sidebar get() = this + "sidebar"
    @SemanticUiCssMarker val item get() = this + "item"
    @SemanticUiCssMarker val items get() = this + "items"
    @SemanticUiCssMarker val dimmer get() = this + "dimmer"
    @SemanticUiCssMarker val fullscreen get() = this + "fullscreen"
    @SemanticUiCssMarker val modal get() = this + "modal"
    @SemanticUiCssMarker val modals get() = this + "modals"
    @SemanticUiCssMarker val page get() = this + "page"
    @SemanticUiCssMarker val front get() = this + "front"
    @SemanticUiCssMarker val selection get() = this + "selection"
    @SemanticUiCssMarker val default get() = this + "default"
    @SemanticUiCssMarker val selected get() = this + "selected"
    @SemanticUiCssMarker val search get() = this + "search"
}
