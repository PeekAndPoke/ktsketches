package de.peekandpoke.ultra.depot

import de.peekandpoke.ultra.common.Lookup
import kotlin.reflect.KClass

/**
 * The depot
 */
class Depot(private val drivers: Lookup<DepotRepository>) {

    /**
     * Gets all repositories
     */
    fun getAllRepos() = drivers.all()

    /**
     * Gets a repository by it's [name]
     */
    fun getRepo(name: String): DepotRepository? = drivers.all().firstOrNull { it.name == name }

    /**
     * Gets a repository by [cls]
     */
    fun <T : DepotRepository> getRepo(cls: KClass<T>): T? = drivers.getOrNull(cls)

    /**
     * Gets a [DepotFile] by the given [DepotUri]
     */
    fun getFile(uri: DepotUri): DepotFile? {
        return getRepo(uri.repo)?.getBucket(uri.bucket)?.getFile(uri.file)
    }
}
