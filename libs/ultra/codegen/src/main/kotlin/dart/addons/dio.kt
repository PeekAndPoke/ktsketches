package de.peekandpoke.ultra.codegen.dart.addons

import de.peekandpoke.ultra.codegen.dart.*
import de.peekandpoke.ultra.codegen.tagged
import kotlin.reflect.KClass
import kotlin.reflect.KType

fun DartFile.Definition.importDio() {
    addImport("package:dio/dio.dart")
}

fun DartFile.Definition.importDioApiResponseClasses() {
    project.findFirstClassByTag("dio:ApiResponse").import()
}

fun DartFile.Definition.createDioApiResponseClasses() {

    addClass("ApiResponse", tagged("dio:ApiResponse")) {
        val typeParam = DartNamedType("T")
        addTypeParameter(typeParam)
        addProperty(typeParam, "data")

        addDefaultConstructor()
    }
}

fun DartFile.Definition.addDioApplyParamsHelper() {
    addFunction("_applyParams") {
        returnType(DartString)
        addParameter(DartString, "uri")
        addParameter(DartMapType(DartString, DartString), "params")
        body(
            """
                var rest = <String>[];
                
                params.forEach((key, value) {
                
                  var valueEnc = Uri.encodeComponent(value);
                  var pattern = '{${"$"}{key}}';
                
                  if (uri.contains(pattern)) {
                    uri = uri.replaceAll(pattern, valueEnc);
                  } else {
                    rest.add(
                        '${"$"}{key}=${"$"}{valueEnc}'
                    );
                  }
                });
                
                if (rest.isEmpty) {
                  return uri;
                }
                
                return uri + '?' + rest.join('&');
            """.trimIndent()
        )
    }
}

fun DartClass.Definition.addDioEndpointFunction(
    /** The function name */
    name: String,
    /** The http method */
    httpMethod: String,
    /** The uri pattern */
    pattern: String,
    /** Params where the key is the param name and the value if the params is optional */
    params: List<Pair<String, Boolean>>,
    /** The type of the request body */
    bodyType: KType?,
    /** The return type of request */
    returnType: KType
) {
    file.importDartConvert()
    file.importDio()

    val bodyCls = bodyType?.classifier as? KClass<*>
    val bodyDartType = bodyCls?.let { file.useClass(bodyCls) }

    addFunction(name) {
        async()

        val dartReturnType = addResultType(returnType)

        params.forEach { (name, optional) ->
            when (optional) {
                false -> addParameter(DartString, name)
                else -> addParameter(DartString, name) { append("''") }
            }
        }

        // When the request has a body, we create a parameter for it
        bodyDartType?.let { body ->
            addParameter(body, "requestBody")
        }

        body {
            appendLine("// ignore: unnecessary_this")
            append("var response = await dio().${httpMethod.toLowerCase()}(").indent {
                append("_applyParams('$pattern', {").indent {
                    // Add all request parameters
                    params.forEach { (name, _) ->
                        append("'").append(name).append("'").append(": ").append(name).append(",").nl()
                    }
                }.appendLine("}),")

                // When the request has a body we also add it to the Dio.xxx("...", data: ...)
                bodyDartType?.let {
                    append("data: requestBody.toJson(),").nl()
                }

            }.appendLine(");")

            nl()

            appendLine("var data = response.data as Map<String, dynamic>;")

            when (returnType.classifier) {
                List::class -> {
                    val innerResolved = file.resolveDartType(returnType.arguments[0].type!!)

                    appendLine("var dataData = data['data'] as List<dynamic>;")
                    append("var content = ").append("dataData.map((it) => ").append(innerResolved.type).append(".fromJson(it as Map<String, dynamic>)").append(").toList();")
                    nl()
                }

                else -> {
                    append("var content = ").append(dartReturnType).append(".fromJson(data['data'] as Map<String, dynamic>);")
                    nl()
                }
            }
            nl()

            append("return Future.value(").indent {
                appendLine("ApiResponse(content)")
            }.append(");")
        }
    }

}

private fun DartFunction.Definition.addResultType(type: KType): DartType {
    val resolved = file.resolveDartType(type)

    file.importDioApiResponseClasses()

    if (resolved.failed) {
        addDoc("Failed to resolve type '${type}'")
    }

    returnType(
        DartGenericType(
            DartNamedType("Future"),
            DartGenericType(
                DartNamedType("ApiResponse"),
                resolved.type
            )
        )
    )

    return resolved.type
}
