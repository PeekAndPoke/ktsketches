package de.peekandpoke.ultra.depot

import java.time.Instant

/**
 * Definition of a depot file
 */
interface DepotFile {
    /** The bucket the file belongs to */
    val bucket: DepotBucket

    /** The name of the file */
    val name: String

    /** The size of the file in bytes */
    val size: Long?

    /** When was the file last modified */
    val lastModifiedAt: Instant?

    /** Fully qualifying uri of the file */
    val uri: DepotUri get() = DepotUri.of(this)

    /** Gets the content of the file */
    fun getContent(): DepotFileContent?
}
