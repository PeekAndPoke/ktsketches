package de.peekandpoke.kraft.addons.routing

import de.peekandpoke.ultra.jshelpers.decodeURIComponent
import de.peekandpoke.ultra.jshelpers.encodeURIComponent

/**
 * Common Route representation
 */
interface Route {

    /**
     * Represents a route match
     */
    data class Match(
        val route: Route,
        val params: Map<String, String>
    ) {
        companion object {
            val default = Match(route = Route.default, params = emptyMap())
        }

        /** Shortcut to the pattern of the [route] */
        val pattern = route.pattern

        /** Gets the route param with the given [key] / name */
        operator fun get(key: String) = param(key)

        /** Gets the route param with the given [name] or defaults to [default] */
        fun param(name: String, default: String = ""): String = params[name] ?: default
    }

    companion object {
        /** Basic default route */
        val default = Static("")
    }

    /** The pattern of the route */
    val pattern: String

    /**
     * Matches the given [uri] against the [pattern] of the route.
     *
     * Returns an instance of [Match] or null if the pattern did not match.
     */
    fun match(uri: String): Match?

    /**
     * Internal helper for building uris
     */
    fun String.replacePlaceholder(placeholder: String, value: String) =
        replace("{$placeholder}", encodeURIComponent(value))
}

/**
 * A parameterized route with one route parameter
 */
abstract class RouteBase(final override val pattern: String, numParams: Int) : Route {

    companion object {
        val placeholderRegex = "\\{([^}]*)}".toRegex()
        const val extractRegexPattern = "([^/]*)"
    }

    /**
     * We extract all placeholders from the pattern
     */
    private val placeholders = placeholderRegex
        .findAll(pattern)
        .map { it.groupValues[1] }
        .toList()

    /**
     * We construct a regex for matching the whole pattern with param placeholders
     */
    private val matchingRegex =
        placeholders.fold(pattern) { acc, placeholder ->
            acc.replace("{$placeholder}", extractRegexPattern)
        }.replace("/", "\\/").toRegex()


    init {
        // Sanity check
        if (numParams != placeholders.size) {
            error("The route '$pattern' has [${placeholders.size}] route-params but should have [$numParams]")
        }
    }

    /**
     * Tries to match the given [uri] against the [pattern] of the route.
     */
    override fun match(uri: String): Route.Match? {

        val match = matchingRegex.matchEntire(uri) ?: return null

//        console.log(placeholders)
//        console.log(match)
//        console.log(match.groupValues)

        val params = placeholders.zip(
            match.groupValues.drop(1).map(::decodeURIComponent)
        ).toMap()

        return Route.Match(route = this, params = params)
    }

    /**
     * Internal helper for building an uri with the given [params]
     */
    protected fun buildUri(vararg params: String) =
        params.foldIndexed("#$pattern") { idx, pattern, param ->
            pattern.replacePlaceholder(placeholders[idx], param)
        }
}


/**
 * A static route is a route that does not have any route parameters
 */
open class Static(pattern: String) : RouteBase(pattern, 0) {
    operator fun invoke() = buildUri()
}

/**
 * A parameterized route with one route parameter
 */
open class Route1(pattern: String) : RouteBase(pattern, 1)

/**
 * A parameterized route with two route parameter
 */
open class Route2(pattern: String) : RouteBase(pattern, 2)

/**
 * A parameterized route with two route parameter
 */
open class Route3(pattern: String) : RouteBase(pattern, 3)

/**
 * A parameterized route with two route parameter
 */
open class Route4(pattern: String) : RouteBase(pattern, 4)
