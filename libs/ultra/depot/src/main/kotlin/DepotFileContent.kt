package de.peekandpoke.ultra.depot

/**
 * Definition of the content of a depot file
 */
interface DepotFileContent {
    /** Returns the content of the file as a [ByteArray] */
    fun getContentBytes(): ByteArray
}
