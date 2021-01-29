package de.peekandpoke.ultra.depot.repos

import com.fasterxml.jackson.annotation.JsonIgnore

data class AwsS3Config(
    val region: String,
    val accessKeyId: String,
    @get:JsonIgnore // Ignored so it will now show up in the logs or insights panel
    val secretAccessKey: String
)
