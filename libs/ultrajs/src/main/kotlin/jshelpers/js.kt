package de.peekandpoke.ultra.jshelpers

val jsObject get() = js("({})")
val jsArray get() = js("([])")

fun jsObjectOf(vararg pairs: Pair<String, dynamic>): dynamic {
    val obj = jsObject

    pairs.forEach { (k, v) -> obj[k] = v }

    return obj
}

/**
 * Converts a map to a raw javascript object
 */
val <T> Map<String, T>.js
    get() : dynamic {
        val obj = jsObject

        forEach { (k, v) -> obj[k] = v }

        return obj
    }

val <T> List<T>.js
    get(): dynamic {
        val arr = jsArray

        @Suppress("UnsafeCastFromDynamic")
        forEach { arr.push(it) }

        return arr
    }
