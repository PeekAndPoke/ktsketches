package de.peekandpoke.ultra.vault.lang

import de.peekandpoke.ultra.common.reflection.TypeRef
import de.peekandpoke.ultra.common.reflection.kType

@Suppress("unused")
data class PropertyPath<P, T>(
    val previous: PropertyPath<*, *>?,
    val current: Step<T>
) : Expression<T> {

    companion object {
        fun <T> start(root: Expression<T>) = PropertyPath<T, T>(null, ExprStep(root))
    }

    abstract class Step<T>(private val type: TypeRef<T>) : Expression<T> {
        override fun getType() = type
    }

    class PropStep<T>(private val name: String, type: TypeRef<T>) : Step<T>(type) {
        override fun print(p: Printer) = p.append(".").name(name)
    }

    class ExprStep<T>(private val expr: Expression<T>) : Step<T>(expr.getType()) {
        override fun print(p: Printer) = p.append(expr)
    }

    class ArrayOpStep<T>(private val op: String, type: TypeRef<T>) : Step<T>(type) {
        override fun print(p: Printer) = p.append(op)
    }

    override fun getType() = current.getType()

    override fun print(p: Printer) {

        previous?.apply { p.append(this) }

        p.append(current)
    }

    inline fun <NF, reified NT> append(prop: String) = PropertyPath<NF, NT>(this, PropStep(prop, kType()))
}
