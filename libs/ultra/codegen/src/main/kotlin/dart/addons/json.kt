package de.peekandpoke.ultra.codegen.dart.addons

import de.peekandpoke.ultra.codegen.dart.*
import kotlinx.serialization.SerialName
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf


fun DartFile.Definition.importJsonAnnotation() {
    addImport("package:json_annotation/json_annotation.dart")
}

fun DartClass.Definition.makeSerializable() {
    file.importJsonAnnotation()
    file.importJsonPart()
    addJsonSerializableAnnotation()
    addJsonFactory()
    addToJson()
}

fun DartFile.Definition.importJsonPart() {
    addPartImport("$fileName.g.dart")
}

fun DartClass.Definition.addJsonSerializableAnnotation() {

    file.importJsonAnnotation()

    addAnnotation("@JsonSerializable(explicitToJson: true)")
}

fun DartClass.Definition.addToJson(discriminatorName: String = "_type") {

    val thisClass = tags.tags.filterIsInstance<KClass<*>>().firstOrNull()
    val serialName = thisClass?.findAnnotation<SerialName>()

    addFunction("toJson") {
        returnType(
            DartMapType.string2dynamic
        )

        body {
            if (serialName != null) {
                append("var map = _${"$"}${this@addToJson.name}ToJson(this);").nl()
                append("map['$discriminatorName'] = '${serialName.value}';").nl()
                nl()
                append("return map;")

            } else {
                append("return _${"$"}${this@addToJson.name}ToJson(this);")
            }
        }
    }
}

fun DartClass.Definition.addJsonFactory(discriminatorName: String = "_type") {

    val thisClass = tags.tags.filterIsInstance<KClass<*>>().firstOrNull()
    val allClasses = file.project.findAllClassDefinitions()

    val childClasses: List<Pair<String, KClass<*>>> = when {
        thisClass != null -> {
            allClasses.mapNotNull { cls ->

                val childClass = cls.tags.tags.filterIsInstance<KClass<*>>().firstOrNull()

                when {
                    childClass != null && childClass != thisClass && childClass.isSubclassOf(thisClass) -> {

                        childClass.findAnnotation<SerialName>()?.let {
                            it.value to childClass
                        }
                    }

                    else -> null
                }
            }
        }

        else -> emptyList()
    }

    addFactoryFunction("fromJson") {

        addParameter(DartMapType.string2dynamic, "json")

        if (childClasses.isEmpty()) {
            body {
                append("return _${'$'}${this@addJsonFactory.name}FromJson(json);")
            }
        } else {
            // make sure all child classes are imported
            childClasses.forEach { (_, cls) ->
                file.useClass(cls)
            }

            addDoc("Has ${childClasses.size} polymorphic child classes. Using type discriminator '${discriminatorName}'.")
            addDoc("Child classes are:")
            childClasses.forEachIndexed { index, it ->
                addDoc("  ${index + 1}. '${it.first}' to '${it.second.qualifiedName}'")
            }

            body {
                append("switch ('${"$"}{json['$discriminatorName']}') {").indent {

                    childClasses.forEach { (id, cls) ->
                        val imported = file.useClass(cls)
                        append("case '$id': ").indent {
                            append("return ").append(imported).append(".fromJson(json);")
                            nl()
                        }
                    }
                }
                append("}")
                nl()

                // Default clause
                append("return _${'$'}${this@addJsonFactory.name}FromJson(json);")
            }
        }
    }
}
