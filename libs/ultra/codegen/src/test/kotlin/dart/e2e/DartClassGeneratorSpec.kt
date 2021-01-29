package de.peekandpoke.ultra.codegen.dart.e2e

import de.peekandpoke.ultra.codegen.dart.*
import de.peekandpoke.ultra.codegen.dartProject
import de.peekandpoke.ultra.codegen.shouldHaveNoDiffs
import io.kotlintest.assertSoftly
import io.kotlintest.specs.StringSpec

class DartClassGeneratorSpec : StringSpec({

    // TODO: test fun imports
    // TODO: test for functions with and without return types
    // TODO: test for default constructor
    // TODO: test for factory functions

    // TODO: tests for imports of generated files
    // TODO: tests for imports of cyclic dependencies

    // TODO: tests for JSON extension functions

    "Generating a simple class" {

        val project = dartProject("test") {

            file("examples.dart") {
                addClass("MyClass") {}
            }
        }

        val printed = project.printFiles()

        assertSoftly {
            printed[0].content shouldHaveNoDiffs """
                class MyClass {
                }
            """.trimIndent()
        }
    }

    "Generating a simple class with some properties" {

        val project = dartProject("test") {

            file("examples.dart") {
                addClass("MyClass") {
                    addProperty(DartString, "pString")
                    addProperty(DartDouble, "pDouble")
                    addProperty(DartDynamic, "pDynamic")
                    addProperty(DartMapType.string2dynamic, "pMapStringDynamic")
                    addProperty(DartMapType(DartString, DartListType(DartDynamic)), "pMapStringToListDynamic")
                }
            }
        }

        val printed = project.printFiles()

        assertSoftly {
            printed[0].content shouldHaveNoDiffs """ 
                class MyClass {
                  String pString;
                  double pDouble;
                  dynamic pDynamic;
                  Map<String, dynamic> pMapStringDynamic;
                  Map<String, List<dynamic>> pMapStringToListDynamic;
                }
            """.trimIndent()
        }
    }
})
