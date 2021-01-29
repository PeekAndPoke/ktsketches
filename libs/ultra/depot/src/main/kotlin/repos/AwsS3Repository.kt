package de.peekandpoke.ultra.depot.repos

import de.peekandpoke.ultra.depot.DepotBucket
import de.peekandpoke.ultra.depot.DepotFile
import de.peekandpoke.ultra.depot.DepotFileContent
import de.peekandpoke.ultra.depot.DepotRepository
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import java.time.Instant

/**
 * Depot repository implementation for storing files in AWS S3.
 *
 * The repo needs an [S3Client] to work with.
 * The repo also works on exactly one bucket specified by [bucketName].
 *
 * How to use the AWS-SDK-2:
 * See https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/javav2/example_code/s3
 */
abstract class AwsS3Repository(private val s3: S3Client, private val bucketName: String) : DepotRepository {

    /**
     * Implementation of [DepotBucket] for S3 repos.
     *
     * NOTICE: do not confuse AWS-S3 buckets with Depot buckets
     */
    data class DepotS3Bucket(
        override val repo: AwsS3Repository,
        val s3: S3Client,
        val bucket: Bucket,
        val prefix: String
    ) : DepotBucket {
        /** The name of the depot bucket == shared prefix of S3 objects */
        override val name: String get() = prefix

        /** When was the bucket last modified / created */
        override val lastModifiedAt: Instant? get() = bucket.creationDate()

        /**
         * Lists all files in the bucket
         */
        override fun listFiles(): List<DepotFile> {
            val listObjects = ListObjectsRequest.builder()
                .bucket(bucket.name())
                .prefix(prefix)
                .delimiter("/")
                .build()

            return s3.listObjects(listObjects).contents()
                .filter { it.key() != prefix }
                .map { DepotS3File(s3 = s3, bucket = this, obj = it) }
        }

        /**
         * Lists [limit] newest files in the bucket
         */
        override fun listNewest(limit: Int): List<DepotFile> {
            val listObjects = ListObjectsRequest.builder()
                .bucket(bucket.name())
                .prefix(prefix)
                .delimiter("/")
                .maxKeys(limit)
                .build()

            return s3.listObjects(listObjects).contents()
                .filter { it.key() != prefix }
                .map { DepotS3File(s3 = s3, bucket = this, obj = it) }
        }

        /**
         * Gets a file by the given [name]
         */
        override fun getFile(name: String): DepotFile? {

            val fullKey = prefix + name

            val request = GetObjectRequest
                .builder()
                .bucket(bucket.name())
                .key(fullKey)
                .build()

            val obj = s3.getObject(request).response()

            return DepotS3File(
                s3 = s3,
                bucket = this,
                obj = S3Object.builder()
                    .key(fullKey)
                    .size(obj.contentLength())
                    .lastModified(obj.lastModified())
                    .build()
            )
        }

        /**
         * Gets the [DepotFileContent] of the file with the given [name]
         */
        override fun getContent(name: String): DepotS3FileContent? {

            val fullKey = prefix + name

            val request = GetObjectRequest
                .builder()
                .bucket(bucket.name())
                .key(fullKey)
                .build()

            return DepotS3FileContent(s3 = s3, request = request)
        }

        /**
         * Puts a file with the given [name] and [content] into the bucket.
         */
        override fun putFile(name: String, content: ByteArray): DepotFile {

            val fullKey = prefix + name

            val request = PutObjectRequest.builder()
                .bucket(bucket.name())
                .key(fullKey)
                .build()

            val response = s3.putObject(request, RequestBody.fromBytes(content))

            return DepotS3File(
                s3 = s3,
                bucket = this,
                obj = S3Object.builder()
                    .key(fullKey)
                    .eTag(response.eTag())
                    .size(content.size.toLong())
                    .build()
            )
        }

        /**
         * Puts a file with the given [name] and [content] into the bucket.
         */
        override fun putFile(name: String, content: String): DepotFile {

            val fullKey = prefix + name

            val request = PutObjectRequest.builder()
                .bucket(bucket.name())
                .key(fullKey)
                .build()

            val response = s3.putObject(request, RequestBody.fromString(content))

            return DepotS3File(
                s3 = s3,
                bucket = this,
                obj = S3Object.builder()
                    .key(fullKey)
                    .eTag(response.eTag())
                    .size(content.length.toLong())
                    .build()
            )
        }

        /** Remove a file with a certain uri */
        override fun removeFile(name: String) : Boolean {
            val fullKey = prefix + name

            try {
                s3.deleteObject(
                    DeleteObjectRequest.builder()
                        .bucket(bucket.name())
                        .key(fullKey)
                        .build()
                )

                return true;
            } catch (e: Exception) {
            }

            return false;
        }
    }

    /**
     * Imlementation of [DepotFileContent] for S3 repos.
     */
    data class DepotS3FileContent(val s3: S3Client, val request: GetObjectRequest) : DepotFileContent {
        /**
         * Read the contents of the file as [ByteArray]
         */
        override fun getContentBytes(): ByteArray {
            val objectBytes = s3.getObjectAsBytes(request)

            return objectBytes.asByteArray()
        }
    }

    /**
     * Implementation of [DepotFile] for S3 repos.
     */
    data class DepotS3File(val s3: S3Client, override val bucket: DepotS3Bucket, val obj: S3Object) : DepotFile {

        /** Name of the file */
        override val name: String
            get() = obj.key().removePrefix(bucket.prefix)

        /** The size of the object in bytes */
        override val size: Long?
            get() = obj.size()

        /** Last modification of the file */
        override val lastModifiedAt: Instant?
            get() = obj.lastModified()

        /** Get the contents of the file */
        override fun getContent(): DepotFileContent? {
            return bucket.getContent(name)
        }
    }

    /** The repo type */
    override val type = "S3"

    /** The name of the repo is always the bucket name */
    override val name: String = bucketName

    /** The location of the repo */
    override val location: String get() = "AWS S3 '$name'"

    /** We always work on the [rootBucket] */
    private val rootBucket by lazy {
        s3.listBuckets().buckets().first { it.name() == bucketName }
    }

    /**
     * Lists all buckets (top level folders)
     */
    override fun listBuckets(): List<DepotBucket> {

        val listObjects = ListObjectsRequest
            .builder()
            .bucket(rootBucket.name())
            .delimiter("/")
            .build()

        return s3.listObjects(listObjects).commonPrefixes().map {
            DepotS3Bucket(
                repo = this,
                s3 = s3,
                bucket = rootBucket,
                it.prefix()
            )
        }
    }

    /**
     * Lists newest buckets (top level folders)
     */
    override fun listNewest(limit: Int): List<DepotBucket> {
        return listBuckets().sortedBy { it.lastModifiedAt }
    }

    /**
     * Gets a bucket (top level folder)
     */
    override fun getBucket(bucketName: String): DepotBucket {
        return DepotS3Bucket(
            repo = this,
            s3 = s3,
            bucket = rootBucket,
            prefix = bucketName.trimEnd('/') + "/"
        )
    }

    /**
     * Creates a bucket
     */
    override fun createBucket(bucketName: String): DepotBucket {
        return getBucket(bucketName)
    }
}
