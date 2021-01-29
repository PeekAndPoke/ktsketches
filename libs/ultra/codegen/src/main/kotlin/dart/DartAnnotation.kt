package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter

class DartAnnotation(
    val complete: String
) : DartPrintable {

    class Definition(val complete: String) {
        fun implement() = DartAnnotation(
            complete = complete
        )
    }

    override fun print(printer: DartCodePrinter) = printer {
        appendLine(complete)
    }
}
