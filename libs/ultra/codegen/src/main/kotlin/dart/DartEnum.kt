package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.Tags
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter

class DartEnum(
    override val file: DartFile.Definition,
    override val tags: Tags,
    val name: String,
    val values: List<Value>,
) : DartFileElement {

    class Definition(
        override val file: DartFile.Definition,
        override val tags: Tags,
        val name: String,
        val builder: Definition.() -> Unit
    ) : DartFileElement.Definition {

        private val values = mutableListOf<Value>()

        override fun implement() = apply(builder).run {
            DartEnum(
                file = file,
                tags = tags,
                name = name,
                values = values
            )
        }

        fun addValues(vararg names: String) {
            values.addAll(names.map { Value(it) })
        }
    }

    class Value(val name: String) : DartPrintable {
        override fun print(printer: DartCodePrinter) = printer {
            append(name)
        }
    }

    override val info: String
        get() = name

    override fun print(printer: DartCodePrinter) = printer {

        append("enum ").append(name).append(" {").indent {
            values.forEach {
                append(it).append(", // ignore: constant_identifier_names")
                nl()
            }
        }
        append("}")
        nl()
    }
}
