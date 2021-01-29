package de.peekandpoke.ultra.depot

/**
 * Definition of a depot repository
 */
interface DepotRepository {
    /** Unique name of the repository */
    val name: String

    /** Human readable type of the repository */
    val type: String

    /** Human readable storage location of the repository */
    val location: String

    /** Lists all [DepotBucket]s in the repo. */
    fun listBuckets(): List<DepotBucket>

    /** Lists the newest [limit] buckets in the repo. */
    fun listNewest(limit: Int = 100): List<DepotBucket>

    /** Gets the bucket with the given [name] */
    fun getBucket(bucketName: String): DepotBucket

    /** Creates the bucket with the given [name] */
    fun createBucket(bucketName: String): DepotBucket
}
