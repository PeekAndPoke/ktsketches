package de.peekandpoke.kraft.store.addons

import de.peekandpoke.kraft.store.MappingStreamWrapper
import de.peekandpoke.kraft.store.Stream

fun <IN, OUT> Stream<IN>.map(mapper: (IN) -> OUT): Stream<OUT> = StreamMapper(this, mapper)

private class StreamMapper<IN, OUT>(wrapped: Stream<IN>, mapper: (IN) -> OUT) : MappingStreamWrapper<IN, OUT>(wrapped, mapper)
