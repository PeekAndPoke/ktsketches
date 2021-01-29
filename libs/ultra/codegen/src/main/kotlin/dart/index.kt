package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter

interface DartPrintable {
    fun print(printer: DartCodePrinter): Any?
}

class DartPrintableString(val string: String) : DartPrintable {
    override fun print(printer: DartCodePrinter) = printer {
        append(string)
    }
}

@Suppress("EnumEntryName")
enum class DartModifier {
    abstract,
    final
}
