package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.common.reflection.TypeRef
import de.peekandpoke.ultra.vault.lang.Aliased
import de.peekandpoke.ultra.vault.lang.Expression
import de.peekandpoke.ultra.vault.lang.Printer
import kotlin.reflect.KClassifier

interface Repository<T> : Expression<List<T>>, Aliased {

    /**
     * The name of the repository
     */
    val name: String

    /**
     * The type of the entities stored in the repository.
     *
     * This is needed for deserialization
     */
    val storedType: TypeRef<T>

    /**
     * Helper for accessing the repos this pointer
     */
    val repo get() = this

    /**
     * Returns the expression type of the repo
     */
    override fun getType(): TypeRef<List<T>> = storedType.list

    /**
     * Prints the repo as part of a query
     */
    override fun print(p: Printer) {
        p.name(name)
    }

    /**
     * Gets the alias
     */
    override fun getAlias(): String {
        return name
    }

    /**
     * Ensures that the repository is set up properly
     *
     * e.g. creating a database collection, ensuring indexes, ...
     */
    fun ensure()

    /**
     * Checks whether the repository stores the given cls
     */
    fun stores(type: KClassifier): Boolean = type == storedType.type.classifier

    /**
     * Retrieves a document by id or null if there is none.
     */
    fun findById(id: String?): Stored<T>?

    /**
     * Inserts the given object into the database and returns the saved version
     */
    fun <X : T> save(new: New<X>): Stored<X>

    /**
     * Updates the given obj in the database and returns the saved version
     */
    fun <X : T> save(stored: Stored<X>): Stored<X>

    /**
     * Saves the given storable and returns the saved version
     */
    fun <X : T> save(storable: Storable<X>): Stored<X> = when (storable) {
        is New<X> -> save(storable)

        is Stored<X> -> save(storable)

        is Ref<X> -> save(storable.asStored)
    }

    /**
     * Inserts the given object into the database and returns the saved version
     */
    fun <X : T> save(new: X): Stored<X> = save(New(new))

    /**
     * Inserts the given object into the database and returns the saved version
     */
    fun <X : T> save(new: X, modify: (X) -> X): Stored<X> = save(New(modify(new)))

    /**
     * Inserts the given object with the given key into the database and returns the saved version
     */
    fun <X : T> save(key: String, new: X): Stored<X> = save(New(_key = key, value = new))

    /**
     * Save the given [stored] and applying the given [modify] before storing it.
     *
     * Returns the saved version
     */
    fun <X : T> save(stored: Storable<X>, modify: (X) -> X): Stored<X> = save(
        stored.withValue(
            modify(stored.value)
        )
    )

    /**
     * Remove all entries from the collection
     */
    fun removeAll(): RemoveResult
}
