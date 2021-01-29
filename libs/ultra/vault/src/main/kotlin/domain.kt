@file:Suppress("PropertyName")

package de.peekandpoke.ultra.vault

import com.fasterxml.jackson.annotation.JsonIgnore
import de.peekandpoke.ultra.mutator.NotMutable
import de.peekandpoke.ultra.security.user.UserRecord
import de.peekandpoke.ultra.vault.hooks.Timestamps
import de.peekandpoke.ultra.vault.lang.VaultDslMarker

@NotMutable
data class StorableMeta(
    val ts: Timestamps? = null,
    val user: UserRecord? = null
)

@NotMutable
sealed class Storable<T> {
    abstract val value: T
    abstract val _id: String
    abstract val _key: String
    abstract val _rev: String
    abstract val _meta: StorableMeta?

    @get:JsonIgnore
    val collection by lazy {
        _id.split("/").first()
    }

    @get:JsonIgnore
    val asRef: Ref<T> by lazy {
        Ref(value, _id, _key, _rev, _meta)
    }

    @get:JsonIgnore
    val asStored: Stored<T> by lazy {
        Stored(value, _id, _key, _rev, _meta)
    }

    /**
     * Checks if this [Storable] has the same id as the [other]
     */
    @VaultDslMarker
    infix fun hasSameIdAs(other: Storable<T>?) = other != null && _id == other._id

    /**
     * Checks if this [Storable] has another id as the [other]
     */
    @VaultDslMarker
    infix fun hasOtherIdAs(other: Storable<T>) = !hasSameIdAs(other)

    /**
     * Checks if this [Storable] has the an id that is equal to one of the [others]
     */
    @VaultDslMarker
    infix fun hasIdIn(others: List<Storable<T>>) = _id in others.map { it._id }

    abstract fun withValue(newValue: T): Storable<T>

    abstract fun withMeta(newMeta: StorableMeta): Storable<T>
}

@NotMutable
data class Stored<T>(
    override val value: T,
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val _meta: StorableMeta?
) : Storable<T>() {

    fun modify(fn: (oldValue: T) -> T): Stored<T> = withValue(fn(value))

    override fun withValue(newValue: T): Stored<T> = copy(value = newValue)

    override fun withMeta(newMeta: StorableMeta): Stored<T> = copy(_meta = newMeta)
}

@NotMutable
data class Ref<T>(
    override val value: T,
    override val _id: String,
    override val _key: String,
    override val _rev: String,
    override val _meta: StorableMeta?
) : Storable<T>() {

    override fun withValue(newValue: T): Ref<T> = copy(value = newValue)

    override fun withMeta(newMeta: StorableMeta): Ref<T> = copy(_meta = newMeta)
}

@NotMutable
data class New<T>(
    override val value: T,
    override val _id: String = "",
    override val _key: String = "",
    override val _rev: String = "",
    override val _meta: StorableMeta? = null
) : Storable<T>() {

    override fun withValue(newValue: T): New<T> = copy(value = newValue)

    override fun withMeta(newMeta: StorableMeta): New<T> = copy(_meta = newMeta)
}
