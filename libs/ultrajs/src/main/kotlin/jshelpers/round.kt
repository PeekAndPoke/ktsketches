package de.peekandpoke.ultra.jshelpers

import kotlin.math.pow

fun Number.round(digits: Int): Double {

    val factor = 10.0.pow(digits)

    return (this.toDouble() * factor).toInt() / factor
}
