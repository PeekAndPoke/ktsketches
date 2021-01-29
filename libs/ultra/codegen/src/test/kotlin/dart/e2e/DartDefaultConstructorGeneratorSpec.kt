package de.peekandpoke.ultra.codegen.dart.e2e

import de.peekandpoke.ultra.codegen.dart.*
import de.peekandpoke.ultra.codegen.dartProject
import de.peekandpoke.ultra.codegen.shouldHaveNoDiffs
import io.kotlintest.assertSoftly
import io.kotlintest.specs.StringSpec

class DartDefaultConstructorGeneratorSpec : StringSpec({

    "Default constructor for simple class" {

        val project = dartProject("test") {

            file("examples.dart") {
                addClass("MyClass") {
                    addProperty(DartString, "pString")
                    addProperty(DartInt, "pInt")

                    addDefaultConstructor()
                }
            }
        }

        val printed = project.printFiles()

        assertSoftly {
            printed[0].content shouldHaveNoDiffs """
                class MyClass {
                  String pString;
                  int pInt;
                    
                  MyClass(this.pString, this.pInt);
                }
            """.trimIndent()
        }
    }
})
