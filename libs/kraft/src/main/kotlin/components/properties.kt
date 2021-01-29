package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.store.StreamSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty

class ObservableComponentProperty<T>(
    private val component: Component<*>,
    private val initialValue: T,
    private val onChange: ((T) -> Unit)? = null
) : ObservableProperty<T>(initialValue) {

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {

        if (oldValue != newValue) {
            // notify about the change and trigger redraw
            onChange?.invoke(newValue)
            // force the component to redraw
            component.triggerRedraw()
        }
    }

    infix fun setupBy(block: suspend () -> Unit): ObservableComponentProperty<T> = apply {
        GlobalScope.launch { block() }
    }

    infix fun onChange(onChange: (T) -> Unit): ObservableComponentProperty<T> =
        ObservableComponentProperty(component = component, initialValue = initialValue, onChange = onChange)
}

class ObservableStreamProperty<T>(
    val component: Component<*>,
    val stream: StreamSource<T>,
) : ObservableProperty<T>(stream()) {

    override fun afterChange(property: KProperty<*>, oldValue: T, newValue: T) {

        if (oldValue != newValue) {
            // notify about the change and trigger redraw
            stream(newValue)
            // force the component to redraw
            component.triggerRedraw()
        }
    }
}
