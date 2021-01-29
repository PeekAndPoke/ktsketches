package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.StorableMeta
import de.peekandpoke.ultra.vault.lang.IterableExpr
import de.peekandpoke.ultra.vault.lang.PropertyPath
import java.time.Instant

/**
 * Repository marker for recording [Timestamps]
 *
 * Use this annotation on your repo, to signal that [Timestamps] should be set
 * in the meta data of a [Storable]
 */
interface WithTimestamps {
    fun <T> IterableExpr<T>.metaCreatedAt() = PropertyPath.start(this).append<Long, Long>("_meta.ts.createdAt.ts")
}

data class Timestamps(
    val createdAt: Instant?,
    val updatedAt: Instant?
)

class TimestampedOnSaveHook : OnSaveHook {
    @ExperimentalStdlibApi
    override fun <T, X : T> apply(repo: Repository<T>, storable: Storable<X>): Storable<X> {

        if (repo !is WithTimestamps) {
            return storable
        }

        return storable.withMeta(
            update(
                storable._meta ?: StorableMeta()
            )
        )
    }

    private fun update(meta: StorableMeta): StorableMeta {

        val now = Instant.now()

        return when (val current = meta.ts) {

            null -> meta.copy(
                ts = Timestamps(
                    createdAt = now,
                    updatedAt = now
                )
            )

            else -> meta.copy(
                ts = Timestamps(
                    createdAt = current.createdAt ?: now,
                    updatedAt = now
                )
            )
        }
    }
}
