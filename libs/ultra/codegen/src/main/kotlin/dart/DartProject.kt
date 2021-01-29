package de.peekandpoke.ultra.codegen.dart

import de.peekandpoke.ultra.codegen.dart.printer.DartCodePrinter
import de.peekandpoke.ultra.common.ensureDirectory
import java.io.File

class DartProject(
    val packageName: String,
    val files: List<DartFile>
) {
    class Definition(
        val packageName: String
    ) {
        val files = mutableListOf<DartFile.Definition>()

        fun build(): DartProject = DartProject(
            packageName = packageName,
            files = files.map { it.implement() }
        )

        fun file(name: String, builder: DartFile.Definition.() -> Unit) {
            DartFile.Definition(
                this,
                if (name.endsWith(".dart")) name else "$name.dart"
            ).apply { files.add(this) }.apply(builder)
        }

        fun findAllClassDefinitions(): List<DartClass.Definition> = files
            .flatMap { it.elements }
            .filterIsInstance<DartClass.Definition>()

        fun findFirstClassOrNullByTag(tag: Any) = findFirstByTag<DartClass.Definition>(tag)

        fun findFirstClassByTag(tag: Any) = findFirstClassOrNullByTag(tag)
            ?: error("There was no DartClass element found tagged with '$tag'")

        fun findFirstEnumOrNullByTag(tag: Any) = findFirstByTag<DartEnum.Definition>(tag)

        fun findFirstEnumByTag(tag: Any) = findFirstEnumOrNullByTag(tag)
            ?: error("There was no DartEnum element found tagged with '$tag'")

        private inline fun <reified T : DartElement.Definition> findFirstByTag(tag: Any): T? {
            val allElements = files.flatMap { it.elements }
            val allByType = allElements.filterIsInstance<T>()

            @Suppress("UnnecessaryVariable")
            val first = allByType.firstOrNull { it.tags.contains(tag) }

            return first
        }
    }

    /**
     * Prints all code files and returns them (without writing anything to disk)
     */
    fun printFiles(): List<DartCodePrinter.PrintedFile> {
        return files.map { file ->
            DartCodePrinter.print(file)
        }
    }

    fun writeFiles(baseDir: File): List<DartCodePrinter.WrittenFile> {
        val printed = printFiles()

        return printed.map {
            val file = File(baseDir, it.file.path).absoluteFile.normalize()

            file.parentFile.ensureDirectory()

            file.writeText(it.content)

            DartCodePrinter.WrittenFile(file, it)
        }
    }
}
