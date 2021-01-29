package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter

interface DartType : DartPrintable

data class ResolvedDartType(val type: DartType, val successful: Boolean) {

    companion object {
        fun success(type: DartType) = ResolvedDartType(type, true)
        fun failed(type: DartType) = ResolvedDartType(type, false)
    }

    val failed = !successful
}

data class DartNamedType(val name: String) : DartType {
    override fun print(printer: DartCodePrinter) = printer {
        append(name)
    }
}

val DartBoolean = DartNamedType("bool")

val DartDouble = DartNamedType("double")

val DartInt = DartNamedType("int")

val DartDynamic = DartNamedType("dynamic")

val DartString = DartNamedType("String")

val DartDate = DartNamedType("DateTime")

data class DartGenericType(val outer: DartType, val params: List<DartType>) : DartType {

    constructor(outer: DartType, inner: DartType) : this(outer, listOf(inner))

    override fun print(printer: DartCodePrinter) = printer {
        append(outer).append("<")
        append(params) { append(", ") }
        append(">")
    }
}

data class DartClassType(val cls: DartClass.Definition) : DartType {
    override fun print(printer: DartCodePrinter) = printer {
        append(cls.name)
    }
}

data class DartEnumType(val enum: DartEnum.Definition) : DartType {
    override fun print(printer: DartCodePrinter) = printer {
        append(enum.name)
    }
}

data class DartListType(val inner: DartType) : DartType {
    override fun print(printer: DartCodePrinter) = printer {
        append("List<").append(inner).append(">")
    }
}

data class DartMapType(val key: DartType, val value: DartType) : DartType {

    companion object {
        val string2dynamic = DartMapType(DartString, DartDynamic)
    }

    override fun print(printer: DartCodePrinter) = printer {
        append("Map<").append(key).append(", ").append(value).append(">")
    }
}

data class DartSetType(val inner: DartType) : DartType {
    override fun print(printer: DartCodePrinter) = printer {
        append("Set<").append(inner).append(">")
    }
}
