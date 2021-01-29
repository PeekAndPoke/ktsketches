package de.peekandpoke.ultra.vault

import java.lang.reflect.Type

/**
 * Entity cache
 */
interface EntityCache {
    /**
     * Puts the [value] with the given [id] into the cache
     */
    fun <T> put(id: String, value: T): T

    /**
     * Get's or puts the entry for the given [id] by using the [provider] if the [id] is not yet present.
     */
    fun <T> getOrPut(id: String, provider: () -> T?): T?
}

/**
 * Entity Cache that does not do any caching.
 */
object NullEntityCache : EntityCache {
    /**
     * @see EntityCache.put
     */
    override fun <T> put(id: String, value: T): T = value

    /**
     * @see EntityCache.getOrPut
     */
    override fun <T> getOrPut(id: String, provider: () -> T?): T? = provider()
}

/**
 * A default implementation for [EntityCache]
 *
 * The cache will hold as many [entries] as it is given.
 * There is no mechanism like TTL or LRU in place here.
 */
class DefaultEntityCache : EntityCache {

    private val entries = mutableMapOf<String, Any?>()

    /**
     * @see EntityCache.put
     */
    override fun <T> put(id: String, value: T): T = value.also {
        entries[id] = value
    }

    /**
     * @see EntityCache.getOrPut
     */
    override fun <T> getOrPut(id: String, provider: () -> T?): T? {

        if (entries.contains(id)) {
//            println("RefCache HIT $id")

            @Suppress("UNCHECKED_CAST")
            return entries[id] as T
        }

//        println("RefCache MISS $id")

        return provider().apply {
            entries[id] = this
        }
    }
}


class SharedRepoClassLookup {

    private val typeLookup = mutableMapOf<Type, Class<out Repository<*>>?>()

    private val nameLookup = mutableMapOf<String, Class<out Repository<*>>?>()

    fun getOrPut(type: Type, defaultValue: () -> Class<out Repository<*>>?) = typeLookup.getOrPut(type, defaultValue)

    fun getOrPut(name: String, defaultValue: () -> Class<out Repository<*>>?) = nameLookup.getOrPut(name, defaultValue)
}

