package de.peekandpoke.kraft.addons.auth

import de.peekandpoke.ultra.jshelpers.Object
import de.peekandpoke.ultra.jshelpers.jsObjectToMap

@Suppress("FunctionName")
@JsModule("jwt-decode")
@JsNonModule
private external fun jwt_decode(jwt: String): Object?

fun decodeJwtBody(jwt: String): Map<String, Any?> {
    return jsObjectToMap(jwt_decode(jwt))
}

