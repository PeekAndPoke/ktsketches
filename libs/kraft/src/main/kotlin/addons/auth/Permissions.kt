package de.peekandpoke.kraft.addons.auth

import kotlinx.serialization.Serializable

@Serializable
data class Permissions(
    val isLoggedIn: Boolean = false,
    val groups: List<String> = emptyList(),
    val roles: List<String> = emptyList(),
    val permissions: List<String> = emptyList()
)

fun Permissions.hasRole(role: String) = role in this.roles

fun Permissions.hasAnyRole(vararg roles: String) = roles.any { it in this.roles }
