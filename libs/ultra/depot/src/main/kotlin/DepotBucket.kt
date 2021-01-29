package de.peekandpoke.ultra.depot

import java.time.Instant

/**
 * Definition of a depot buckety
 */
interface DepotBucket {

    /** The [DepotRepository] the bucket belongs to */
    val repo: DepotRepository

    /** The buckets name */
    val name: String

    /** The buckets last modification date */
    val lastModifiedAt: Instant?

    /** Lists all files in the bucket */
    fun listFiles(): List<DepotFile>

    /** Lists [limit] newest files in the bucket */
    fun listNewest(limit: Int = 100): List<DepotFile>

    /** Gets the file with the given [name] */
    fun getFile(name: String): DepotFile?

    /** Get the content of the file with the given [name] */
    fun getContent(name: String): DepotFileContent?

    /** Puts a file with the given [name] and [content] */
    fun putFile(name: String, content: String): DepotFile

    /** Puts a file with the given [name] and [content] */
    fun putFile(name: String, content: ByteArray): DepotFile

    /** Remove a file with a certain uri */
    fun removeFile(name: String) : Boolean
}
