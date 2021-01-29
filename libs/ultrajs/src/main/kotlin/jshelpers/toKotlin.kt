package de.peekandpoke.ultra.jshelpers

fun jsObjectToMap(obj: dynamic): Map<String, Any?> {
    return Object.keys(obj as Any)
        .map { it to jsToKotlin(obj[it]) }
        .toMap()
}

fun jsArrayToList(arr: dynamic): List<Any?> {
    val result = mutableListOf<Any?>()

    arr.forEach { item ->
        result.add(jsToKotlin(item))
    }

    return result.toList()
}

fun jsToKotlin(it: dynamic): Any? {
    if (it == null) {
        return null
    }

    if (jsIsArray(it)) {
        return jsArrayToList(it)
    }

    if (jsIsObject(it)) {
        return jsObjectToMap(it)
    }

    @Suppress("UnsafeCastFromDynamic")
    return it
}
