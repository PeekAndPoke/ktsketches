package de.peekandpoke.ultra.depot

data class DepotUri(
    val repo: String,
    val bucket: String,
    val file: String
) {
    val uri: String = "$protocol://${repo.trim('/')}/${bucket.trim('/')}/${file.trim('/')}"

    companion object {

        const val protocol = "depot"

        private val parseRegex = "($protocol://)?([^/]+)/([^/]+)/(.+)".toRegex()

        fun of(file: DepotFile): DepotUri = DepotUri(
            repo = file.bucket.repo.name,
            bucket = file.bucket.name,
            file = file.name,
        )

        fun parse(uri: String): DepotUri? {
            val result = parseRegex.matchEntire(uri) ?: return null

            return DepotUri(
                repo = result.groupValues[2],
                bucket = result.groupValues[3],
                file = result.groupValues[4],
            )
        }
    }
}
