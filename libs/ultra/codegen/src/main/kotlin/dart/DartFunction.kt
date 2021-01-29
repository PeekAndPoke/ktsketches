package de.peekandpoke.ultra.codegen.dart

import dart.DartFunctionParameter
import de.peekandpoke.ultra.codegen.CodeGenDsl
import de.peekandpoke.ultra.codegen.Tags
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrintFn
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter
import de.peekandpoke.ultra.codegen.dart.printer.appendDocBlock

class DartFunction(
    override val file: DartFile.Definition,
    override val tags: Tags,
    val name: String,
    val params: List<DartFunctionParameter>,
    val body: DartCodePrintFn?,
    val returnType: DartType?,
    val docBlock: String,
    val async: Boolean
) : DartFileElement, DartClassElement {

    class Definition(
        override val file: DartFile.Definition,
        override val tags: Tags,
        val name: String,
        val builder: Definition.() -> Unit
    ) : DartFileElement.Definition, DartClassElement.Definition {

        internal val params = mutableListOf<DartFunctionParameter>()
        internal var body: DartCodePrintFn? = null
        internal var returnType: DartType? = null
        internal var async = false

        internal val docBlocks = mutableListOf<String>()

        override fun implement() = apply(builder).run {
            DartFunction(
                file = file,
                tags = tags,
                name = name,
                params = params.toList(),
                body = body,
                returnType = returnType,
                docBlock = docBlocks.joinToString(System.lineSeparator()),
                async = async
            )
        }
    }

    override val info: String
        get() = name

    @Suppress("DuplicatedCode")
    override fun print(printer: DartCodePrinter) = printer {

        appendDocBlock(docBlock)

        returnType?.let { append(it).append(" ") }

        val mandatoryParams = params.filter { it.initialize == null }
        val optionalParams = params.filter { it.initialize != null }

        append(name).append("(").append(mandatoryParams) { append(", ") }

        if (optionalParams.isNotEmpty()) {
            if (mandatoryParams.isNotEmpty()) {
                append(", ")
            }
            append("{").append(optionalParams) { append(", ") }.append("}")
        }

        append(") ")

        if (async) {
            append("async ")
        }

        if (body == null) {
            append("{}")
        } else {
            append("{")
            indent {
                body.invoke(this)
                nl()
            }
            append("}")
        }

        nl()
    }
}

@CodeGenDsl
fun DartFunction.Definition.addParameter(type: DartType, name: String, initialize: DartCodePrintFn? = null) {
    params.add(DartFunctionParameter(type, name, initialize))
}

@CodeGenDsl
fun DartFunction.Definition.addDoc(doc: String) {
    docBlocks.add(doc)
}

@CodeGenDsl
fun DartFunction.Definition.async() {
    async = true
}

@CodeGenDsl
fun DartFunction.Definition.body(str: String) {
    body = { append(str) }
}

@CodeGenDsl
fun DartFunction.Definition.body(block: DartCodePrintFn) {
    body = block
}

@CodeGenDsl
fun DartFunction.Definition.returnType(type: DartType) {
    returnType = type
}
