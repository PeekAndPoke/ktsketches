package de.peekandpoke.kraft.store.addons

import de.peekandpoke.kraft.store.PersistentStreamSource
import de.peekandpoke.kraft.store.StreamSource
import de.peekandpoke.kraft.store.StreamStorage
import kotlinx.browser.window
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import org.w3c.dom.get
import org.w3c.dom.set

private val defaultJson: Json = Json {
    ignoreUnknownKeys = true
}

class LocalStorageStreamStorage<T>(
    private val toStr: (T) -> String,
    private val fromStr: (String?) -> T?,
    private val storageKey: String,
) : StreamStorage<T> {

    override fun read(): T? {
        return fromStr(window.localStorage[storageKey])
    }

    override fun write(value: T) {
        window.localStorage[storageKey] = toStr(value)
    }
}

fun <T> StreamSource<T>.persistInLocalStorage(
    key: String,
    serializer: KSerializer<T>,
    codec: Json = defaultJson
): StreamSource<T> {

    val storage = LocalStorageStreamStorage(
        toStr = {
            codec.encodeToString(serializer, it)
        },
        fromStr = {
            it?.let { nonNull -> codec.decodeFromString(serializer, nonNull) }
        },
        storageKey = key
    )

    return PersistentStreamSource(
        storage = storage,
        wrapped = this
    )
}
