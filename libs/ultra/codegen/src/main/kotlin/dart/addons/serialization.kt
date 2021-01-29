package de.peekandpoke.ultra.codegen.dart.addons

import de.peekandpoke.ultra.codegen.CodeGenDsl
import de.peekandpoke.ultra.codegen.dart.*
import de.peekandpoke.ultra.codegen.joinSimpleNames
import de.peekandpoke.ultra.codegen.tagged
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.isAccessible

@CodeGenDsl
fun DartProject.Definition.addSerializableClasses(builder: DartSerializableCodeGen.Builder.() -> Unit) {
    DartSerializableCodeGen.Builder(this)
        .apply(builder)
        .build()
        .run()
}

class DartSerializableCodeGen(
    val project: DartProject.Definition,
    val packageMapping: (String) -> String,
    val classes: Set<KClass<*>>,
) {
    class Builder(
        val project: DartProject.Definition,
    ) {
        private var packageMapping: (String) -> String = { it }
        private var excludeFilter: (KClass<*>) -> Boolean = { false }

        private val classes = mutableMapOf<String, KClass<*>>()

        fun build() = DartSerializableCodeGen(
            project = project,
            packageMapping = packageMapping,
            classes = classes.values
                .filter { !excludeFilter(it) }
                .toSet()
        )

        fun mapPackages(mapping: (String) -> String) {
            packageMapping = mapping
        }

        /** Set the exclude filter */
        fun exclude(filter: (KClass<*>) -> Boolean) {
            excludeFilter = filter
        }

        /** Add multiple types */
        fun addTypes(types: Iterable<KType>) {
            types.mapNotNull { it.classifier as? KClass<*> }.forEach(::addClass)
        }

        /** Adds a class */
        fun addClass(cls: KClass<*>) {
            classes[cls.qualifiedName!!] = cls
        }
    }

    fun run() {

        val groupedByFile = classes.groupBy { it.mapPackageToFile() }

        project.apply {

            groupedByFile.forEach { (path, classes) ->
                file(path) {
                    classes.forEach { cls -> addType(cls) }
                }
            }
        }
    }

    private fun KClass<*>.mapPackageToFile() = packageMapping(java.`package`.name).replace(".", "/") + ".dart"

    private fun DartFile.Definition.addType(cls: KClass<*>) {
        when {
            cls.java.isEnum -> addEnum(cls)

            else -> addClass(cls)
        }
    }

    private fun DartFile.Definition.addEnum(cls: KClass<*>) {

        addEnum(cls.joinSimpleNames(), tagged(cls)) {
            addValues(
                *cls.java.enumConstants.map { it.toString() }.toTypedArray()
            )
        }
    }

    private fun DartFile.Definition.addClass(cls: KClass<*>) {

        addClass(cls.joinSimpleNames(), tagged(cls)) {

            addDoc("Source class: [${cls.qualifiedName}]")

            applyExtends(cls)

            when {
                cls.isData -> {
                    cls.primaryConstructor!!.parameters
                        .map {
                            addProperty(it.type, it.name!!.removePrefix("_")) {
                                if (it.name!!.startsWith("_")) {
                                    addAnnotation("@JsonKey(name: '${it.name}')")
                                }
                            }
                        }
                }

                cls.isAbstract -> {
                    cls.declaredMemberProperties.filter { !it.isAccessible }
                        .map {
                            addProperty(it.returnType, it.name.removePrefix("_")) {
                                if (it.name.startsWith("_")) {
                                    addAnnotation("@JsonKey(name: '${it.name}')")
                                }
                            }
                        }
                }
            }

            addDefaultConstructor()

            makeSerializable()
        }
    }

    private fun DartClass.Definition.applyExtends(cls: KClass<*>) {

        val parentClass = cls.supertypes
            .filter { it.classifier is KClass<*> }
            .firstOrNull { !(it.classifier as KClass<*>).java.isInterface }

        // Everything extends 'kotlin.Any' so we just ignore it
        if (parentClass == null || parentClass.classifier == Any::class) {
            return
        }

        val resolvedParentType = file.resolveDartType(parentClass)
        val parentDartType = resolvedParentType.type

        when (resolvedParentType.successful && parentDartType is DartClassType) {
            true -> extends(parentDartType)
            else -> addDoc("TODO: parent type '$parentClass' could not be resolved.")
        }
    }
}

