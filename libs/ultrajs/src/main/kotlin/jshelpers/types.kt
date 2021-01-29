@file:Suppress("UnsafeCastFromDynamic")

package de.peekandpoke.ultra.jshelpers

fun jsIsObject(o: dynamic): Boolean = jsTypeOf(o) == "object"

@Suppress("UNUSED_PARAMETER")
fun jsIsArray(o: dynamic): Boolean = js("(Array.isArray(o))") as Boolean
