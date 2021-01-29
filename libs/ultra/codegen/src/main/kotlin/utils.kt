package de.peekandpoke.ultra.codegen

import kotlin.reflect.KClass

@DslMarker
annotation class CodeGenDsl

// TODO: move to ultra::common
fun KClass<*>.joinSimpleNames(): String {
    return this.qualifiedName!!
        .replace(this.java.`package`.name, "")
        .replace(".", "")
}
