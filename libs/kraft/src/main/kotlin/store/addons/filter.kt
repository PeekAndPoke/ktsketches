package de.peekandpoke.kraft.store.addons

import de.peekandpoke.kraft.store.MappingStreamWrapper
import de.peekandpoke.kraft.store.Stream

fun <T> Stream<T>.filter(predicate: (T) -> Boolean): Stream<T?> = StreamFilter(this, predicate)

private class StreamFilter<T>(
    wrapped: Stream<T>,
    predicate: (T) -> Boolean
) :
    MappingStreamWrapper<T, T?>(
        wrapped = wrapped,
        mapper = {
            if (predicate(it)) {
                it
            } else {
                null
            }
        }
    )
