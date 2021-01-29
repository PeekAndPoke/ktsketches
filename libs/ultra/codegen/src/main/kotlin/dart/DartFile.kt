package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.CodeGenDsl
import de.peekandpoke.ultra.codegen.Tags
import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter
import de.peekandpoke.ultra.codegen.splash
import kotlin.reflect.KClass
import kotlin.reflect.KType

class DartFile(
    val project: DartProject.Definition,
    val path: String,
    val imports: List<DartImport>,
    val elements: List<DartFileElement>
) : DartPrintable {

    class Definition(
        val project: DartProject.Definition,
        val path: String
    ) {
        val fileName get(): String = path.split("/").last().replace(".dart", "")

        internal val imports = mutableSetOf<DartImport.Definition>()

        internal val elements = mutableListOf<DartFileElement.Definition>()

        fun implement() = DartFile(
            project = project,
            path = path,
            // NOTICE: order is important here! Building elements might create more imports.
            elements = elements.map { it.implement() },
            imports = imports.map { it.implement() }
        )

        ////  RESOLVING and IMPORTING other generated types ////////////////////////////////////////////////////////////

        fun resolveDartType(type: KType): ResolvedDartType {

            val cls = type.classifier as? KClass<*> ?: return ResolvedDartType.failed(DartDynamic)

            return when (cls) {
                Boolean::class -> ResolvedDartType.success(DartBoolean)

                Byte::class, Int::class, Short::class, Long::class -> ResolvedDartType.success(DartInt)

                Float::class, Double::class -> ResolvedDartType.success(DartDouble)

                Char::class, String::class -> ResolvedDartType.success(DartString)

                List::class -> {
                    val inner = type.arguments[0].type!!
                    val innerResolved = resolveDartType(inner)

                    ResolvedDartType(DartListType(innerResolved.type), innerResolved.successful)
                }

                Map::class -> {
                    val key = resolveDartType(type.arguments[0].type!!)
                    val value = resolveDartType(type.arguments[1].type!!)

                    ResolvedDartType(DartMapType(key.type, value.type), key.successful && value.successful)
                }

                Set::class -> {
                    val inner = resolveDartType(type.arguments[0].type!!)

                    ResolvedDartType(DartSetType(inner.type), inner.successful)
                }

                else -> {
                    val dartClass = project.findFirstClassOrNullByTag(cls)
                    val dartEnum = project.findFirstEnumOrNullByTag(cls)

                    when {
                        dartClass != null -> ResolvedDartType.success(useClass(cls))
                        dartEnum != null -> ResolvedDartType.success(useEnum(cls))

                        else -> ResolvedDartType.failed(DartDynamic)
                    }
                }
            }
        }

        fun useClass(tag: Any): DartClassType = project.findFirstClassByTag(tag).import()

        fun useEnum(tag: Any): DartEnumType = project.findFirstEnumByTag(tag).import()

        fun DartClass.Definition.import(): DartClassType {

            // When the class is defined in another file we need to create an import
            if (this.file.path != this@Definition.path) {
                addImport(
                    DartImport.PureDefinition("package:${file.project.packageName}/${file.path}")
                )
            }

            return DartClassType(this)
        }

        fun DartEnum.Definition.import(): DartEnumType {

            // When the class is defined in another file we need to create an import
            if (this.file.path != this@Definition.path) {
                addImport(
                    DartImport.PureDefinition("package:${file.project.packageName}/${file.path}")
                )
            }

            return DartEnumType(this)
        }
    }

    override fun print(printer: DartCodePrinter) = printer {
        append(splash)
        nl(2)
        appendLine("// [INFO] Package:      ${project.packageName}")
        appendLine("// [INFO] File:         $path")
        nl()

        imports
            .sortedWith(compareBy({ it.keyword }, { it.uri }))
            .forEach {
                append(it)
            }

        nl()

        elements.forEach {
            append(it)
            nl()
        }
    }
}

////  IMPORTS and REFERENCES  //////////////////////////////////////////////////////////////////////////////////////////

@CodeGenDsl
fun DartFile.Definition.addImport(import: DartImport.Definition) {
    imports.add(import)
}

@CodeGenDsl
fun DartFile.Definition.addImport(packageUri: String) {
    DartImport.PureDefinition(packageUri).apply { addImport(this) }
}

@CodeGenDsl
fun DartFile.Definition.addPartImport(file: String) {
    DartImport.PartDefinition(file).apply { addImport(this) }
}

////  COMMENTS  /////////////////////////////////////////////////////////////////////////////////////////////////////////////

@CodeGenDsl
fun DartFile.Definition.addComment(comment: String, tags: Tags = Tags.empty) {
    elements.add(
        DartComment.Definition(this, tags) {
            this.comment = comment
        }
    )
}

@CodeGenDsl
fun DartFile.Definition.addComment(tags: Tags = Tags.empty, builder: DartComment.Definition.() -> Unit) {
    elements.add(
        DartComment.Definition(this, tags, builder)
    )
}

////  CLASSES, ENUMS, FUNCTIONS, ...  /////////////////////////////////////////////////////////////////////////////////////

@CodeGenDsl
fun DartFile.Definition.addClass(name: String, tags: Tags = Tags.empty, builder: DartClass.Definition.() -> Unit) {
    elements.add(
        DartClass.Definition(this, tags, name, builder)
    )
}

@CodeGenDsl
fun DartFile.Definition.addEnum(name: String, tags: Tags = Tags.empty, builder: DartEnum.Definition.() -> Unit) {
    elements.add(
        DartEnum.Definition(this, tags, name, builder)
    )
}

@CodeGenDsl
fun DartFile.Definition.addFunction(name: String, tags: Tags = Tags.empty, builder: DartFunction.Definition.() -> Unit) {
    elements.add(
        DartFunction.Definition(this, tags, name, builder)
    )
}

@CodeGenDsl
fun DartFile.Definition.addExtension(name: String, on: DartType, tags: Tags = Tags.empty, builder: DartExtension.Definition.() -> Unit) {
    elements.add(
        DartExtension.Definition(this, tags, name, on, builder)
    )
}
