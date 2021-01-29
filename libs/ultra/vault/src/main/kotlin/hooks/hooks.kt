package de.peekandpoke.ultra.vault.hooks

import de.peekandpoke.ultra.vault.Repository
import de.peekandpoke.ultra.vault.Storable

interface OnSaveHook {
    fun <T, X : T> apply(repo: Repository<T>, storable: Storable<X>): Storable<X>
}
