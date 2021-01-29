package de.peekandpoke.ultra.vault

import de.peekandpoke.ultra.vault.lang.TerminalExpr

data class TypedQuery<T>(val ret: TerminalExpr<T>, val aql: String, val vars: Map<String, Any?>)
