package de.peekandpoke.ultra.vault

/**
 * Defines a repository that supports batch inserts
 */
interface BatchInsertRepository<T> {

    /**
     * Inserts multiple element with a single query.
     *
     * The [values] are pair of KEY to VALUE.
     *
     * The result is a list of the inserted values.
     * The order of the [values] must match the order of the results.
     */
    fun insertBatch(values: List<Pair<String, T>>): Cursor<Stored<T>>
}
