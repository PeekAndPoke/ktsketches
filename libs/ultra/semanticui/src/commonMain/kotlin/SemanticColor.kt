package de.peekandpoke.ultra.semanticui

@Suppress("EnumEntryName", "unused")
enum class SemanticColor {
    default,
    white,
    red,
    orange,
    yellow,
    olive,
    green,
    teal,
    blue,
    violet,
    purple,
    pink,
    brown,
    grey,
    black;

    val isSet get() = this != default
}
