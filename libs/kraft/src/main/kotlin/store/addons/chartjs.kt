@file:JsModule("chart.js")
@file:JsNonModule
package de.peekandpoke.kraft.store.addons

import org.w3c.dom.CanvasRenderingContext2D

external class Chart(
    ctx: CanvasRenderingContext2D,
    data: dynamic
)
