package de.peekandpoke.ultra.codegen.dart

import dart.DartFunctionParameter
import de.peekandpoke.ultra.codegen.Tags
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrintFn
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter

class DartClassConstructor(
    override val tags: Tags,
    val cls: DartClass.Definition,
    val propertyFilter: (DartProperty.Definition) -> Boolean,
    val body: DartCodePrintFn?,
) : DartClassElement {

    class Definition(
        override val tags: Tags,
        val classDef: DartClass.Definition,
        val propertyFilter: (DartProperty.Definition) -> Boolean,
        val body: DartCodePrintFn?,
    ) : DartClassElement.Definition {
        override fun implement(): DartClassConstructor {

            return DartClassConstructor(
                tags = tags,
                cls = classDef,
                propertyFilter = propertyFilter,
                body = body,
            )
        }
    }

    @Suppress("DuplicatedCode")
    override fun print(printer: DartCodePrinter) = printer {

        // Get all properties defines on this class
        val ownParams = cls.getAllProps()
            .filter(propertyFilter)
            .map { DartFunctionParameter(type = it.type, name = it.name) }.toMutableList()

        // Get all properties defined on the parent classes recursively
        val superParams = mutableListOf<DartFunctionParameter>()

        var current = cls.extends

        while (current != null) {
            superParams.addAll(
                index = 0,
                elements = current.cls.getAllProps().map { DartFunctionParameter(type = it.type, name = it.name) }
            )
            current = current.cls.extends
        }

        // Remove all properties of this class that are already defined in parent classes
        ownParams.removeAll { superParams.contains(it) }

        // Print the code
        append(cls.name).append("(").indent {
            append(superParams.plus(ownParams)) { append(",").nl() }
            nl()
        }
        append(")")

        if (superParams.isNotEmpty()) {
            append(" : super(").indent {
                append(superParams.map { it.name }) { append(",").nl() }
                nl()
            }
            append(")")
        }
        append(" {").indent {
            ownParams.forEach {
                appendLine("this.${it.name} = ${it.name};")
            }

            body?.let {
                it()
                nl()
            }
        }
        append("}")
        nl()
    }
}
