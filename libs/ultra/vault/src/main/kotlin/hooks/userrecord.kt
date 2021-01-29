package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.security.user.UserRecordProvider
import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable
import de.peekandpoke.ultra.vault.StorableMeta

/**
 * Repository marker for recording [UserRecord]
 *
 * Use this annotation on your repo, to signal that [UserRecord] should be set
 * in the meta data of a [Storable]
 */
interface WithUserRecord

class UserRecordOnSaveHook(private val provider: UserRecordProvider) : OnSaveHook {

    val user by lazy { provider() }

    @ExperimentalStdlibApi
    override fun <T, X : T> apply(repo: Repository<T>, storable: Storable<X>): Storable<X> {

        if (repo !is WithUserRecord) {
            return storable
        }

        return storable.withMeta(
            (storable._meta ?: StorableMeta()).copy(user = user)
        )
    }
}
