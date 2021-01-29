package dart

import de.peekandpoke.ultra.codegen.dart.DartPrintable
import de.peekandpoke.ultra.codegen.dart.DartType
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrintFn
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter

data class DartFunctionParameter(
    val type: DartType,
    val name: String,
    val initialize: DartCodePrintFn? = null
) : DartPrintable {
    override fun print(printer: DartCodePrinter) = printer {

        append(type).append(" ").append(name)

        initialize?.let {
            append(" = ")
            it.invoke(this)
        }
    }
}
