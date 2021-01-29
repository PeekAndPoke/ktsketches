package de.peekandpoke.ultra.codegen.dart.addons

import de.peekandpoke.ultra.codegen.dart.DartFile
import de.peekandpoke.ultra.codegen.dart.addImport

fun DartFile.Definition.importDartConvert() {
    addImport("dart:convert")
}

