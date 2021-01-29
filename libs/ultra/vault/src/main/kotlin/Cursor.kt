package de.peekandpoke.ultra.vault

interface Cursor<T> : Iterable<T> {
    val query: TypedQuery<T>
    val count: Int
    val timeMs: Double
}
