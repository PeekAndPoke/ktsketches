package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.CodeGenDsl
import de.peekandpoke.ultra.codegen.Tags
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrintFn
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter
import de.peekandpoke.ultra.codegen.dart.printer.appendDocBlock
import de.peekandpoke.ultra.codegen.dart.printer.appendModifiers

class DartProperty(
    override val file: DartFile.Definition,
    override val tags: Tags,
    val name: String,
    val type: DartType,
    val docBlock: String?,
    val modifiers: Set<DartModifier>,
    val annotations: List<DartAnnotation>,
    val initialize: DartCodePrintFn?,
) : DartFileElement, DartClassElement {

    class Definition(
        override val file: DartFile.Definition,
        override val tags: Tags,
        val type: DartType,
        val name: String,
        val builder: Definition.() -> Unit,
    ) : DartFileElement.Definition, DartClassElement.Definition {

        private val docBlocks = mutableListOf<String>()

        private val modifiers = mutableSetOf<DartModifier>()

        private val annotations = mutableListOf<DartAnnotation.Definition>()

        private var initialize: DartCodePrintFn? = null

        override fun implement() = apply(builder).run {
            DartProperty(
                file = file,
                tags = tags,
                name = name,
                type = type,
                docBlock = docBlocks.joinToString(System.lineSeparator()),
                modifiers = modifiers,
                annotations = annotations.map { it.implement() },
                initialize = initialize,
            )
        }

        @CodeGenDsl
        fun final() {
            modifiers.add(DartModifier.final)
        }

        @CodeGenDsl
        fun addDoc(doc: String) {
            docBlocks.add(doc)
        }

        @CodeGenDsl
        fun addAnnotation(complete: String) {
            DartAnnotation.Definition(complete).apply { annotations.add(this) }
        }

        @CodeGenDsl
        fun initialize(code: DartCodePrintFn) {
            initialize = code
        }
    }

    override fun print(printer: DartCodePrinter) = printer {

        appendDocBlock(docBlock)
        append(annotations) { nl() }
        appendModifiers(modifiers).append(type).append(" ").append(name)

        // Add initializer if given
        initialize?.let {
            append(" = ")
            it()
        }

        append(";")

        nl()
    }
}
