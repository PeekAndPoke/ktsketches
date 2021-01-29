package de.peekandpoke.ultra.depot.repos

import de.peekandpoke.ultra.depot.DepotBucket
import de.peekandpoke.ultra.depot.DepotFile
import de.peekandpoke.ultra.depot.DepotFileContent
import de.peekandpoke.ultra.depot.DepotRepository
import java.io.File
import java.time.Instant

abstract class FileSystemRepository(override val name: String, dir: String) : DepotRepository {

    companion object {
        internal fun String.isValidName() = listOf("..", "/", "\\").none { contains(it) }

        internal fun String.validateName() = this.apply {
            if (!isValidName()) {
                error("Invalid name")
            }
        }
    }

    data class FsBucket(
        override val repo: FileSystemRepository,
        override val name: String,
        private val root: File
    ) : DepotBucket {

        override val lastModifiedAt: Instant by lazy {
            Instant.ofEpochMilli(root.lastModified())
        }

        override fun listFiles(): List<DepotFile> {
            return root.listFiles()
                ?.filter { it.isFile }
                ?.map { FsFile(this, it.name.validateName(), it) }
                ?: listOf()
        }

        override fun listNewest(limit: Int): List<DepotFile> {
            return listFiles()
                .sortedBy { it.lastModifiedAt }
                .reversed()
                .take(limit)
        }

        override fun getFile(name: String): DepotFile? {
            // SECURITY. Prevent path traversal! check for any slashes and dot-dots
            name.validateName()

            val file = File(root, name)

            return when {
                file.exists() && file.isFile -> FsFile(this, name, File(root, name))
                else -> null
            }
        }

        override fun getContent(name: String): FsFileContent? {
            // SECURITY. Prevent path traversal! check for any slashes and dot-dots
            name.validateName()

            val file = File(root, name)

            return when {
                file.exists() && file.isFile -> FsFileContent(File(root, name))
                else -> null
            }
        }

        override fun putFile(name: String, content: ByteArray): DepotFile {
            // SECURITY. Prevent path traversal! check for any slashes and dot-dots
            name.validateName()

            val file = File(root, name)

            file.writeBytes(content)

            return FsFile(this, name, file)
        }

        override fun putFile(name: String, content: String): DepotFile {
            // SECURITY. Prevent path traversal! check for any slashes and dot-dots
            name.validateName()

            val file = File(root, name)

            file.writeText(content)

            return FsFile(this, name, file)
        }

        /** Remove a file with a certain uri */
        override fun removeFile(name: String) : Boolean {
            // SECURITY. Prevent path traversal! check for any slashes and dot-dots
            name.validateName()

            val file = File(root, name)

            return file.exists() && file.isFile && file.delete()
        }
    }

    data class FsFileContent(private val file: File) : DepotFileContent {
        override fun getContentBytes(): ByteArray {
            return file.readBytes()
        }
    }

    data class FsFile(
        override val bucket: FsBucket,
        override val name: String,
        private val file: File
    ) : DepotFile {

        override val size: Long? by lazy(LazyThreadSafetyMode.NONE) {
            try {
                file.length()
            } catch (e: SecurityException) {
                null
            }
        }

        override val lastModifiedAt: Instant? by lazy(LazyThreadSafetyMode.NONE) {
            try {
                Instant.ofEpochMilli(file.lastModified())
            } catch (e: SecurityException) {
                null
            }
        }

        override fun getContent(): DepotFileContent? {
            return FsFileContent(file = file)
        }
    }

    private val root = File(dir).absoluteFile

    override val type = "File system"

    override val location: String = root.absolutePath

    override fun listBuckets(): List<DepotBucket> {
        return root.listFiles()
            ?.filter { it.isDirectory }
            ?.map { FsBucket(repo = this, name = it.name.validateName(), root = it) }
            ?: listOf()
    }

    override fun listNewest(limit: Int): List<DepotBucket> {
        return root.listFiles()
            ?.filter { it.isDirectory }
            ?.sortedBy { it.lastModified() }
            ?.reversed()
            ?.take(limit)
            ?.map { FsBucket(repo = this, name = it.name.validateName(), root = it) }
            ?: listOf()
    }

    override fun getBucket(bucketName: String): DepotBucket {
        // SECURITY. Prevent path traversal! check for any slashes and dot-dots
        bucketName.validateName()

        return FsBucket(repo = this, name = bucketName, root = File(root, bucketName))
    }

    override fun createBucket(bucketName: String): DepotBucket {
        // SECURITY. Prevent path traversal! check for any slashes and dot-dots
        bucketName.validateName()

        val bucketDir = File(root.absolutePath, bucketName)

        if (!bucketDir.exists()) {
            bucketDir.mkdirs()
        }

        return FsBucket(repo = this, name = bucketName, root = bucketDir)
    }
}
