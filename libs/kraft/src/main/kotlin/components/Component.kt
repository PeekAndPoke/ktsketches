package de.peekandpoke.kraft.components

import de.peekandpoke.kraft.store.Stream
import de.peekandpoke.kraft.store.StreamSource
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.kraft.vdom.VDomEngine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLElement
import kotlin.properties.ReadOnlyProperty

/**
 * Base class of all Components
 */
@Suppress("FunctionName")
abstract class Component<PROPS>(val ctx: Ctx<PROPS>) {

    /** Accessor for the parent component */
    val parent: Component<*>? get() = _parent

    /** Public getter for the Props */
    val props: PROPS get() = _props

    /** The Dom node to which the component is rendered */
    val dom: HTMLElement? get() = _dom

    /** Message system for child to parent component communication */
    val events = Messages()

    /** Backing field for the [parent] */
    private var _parent: Component<*>? = ctx.parent

    /** Backing field for the [props] */
    private var _props: PROPS = ctx.props

    /** Backing field for the [dom] */
    private var _dom: HTMLElement? = null

    /** Flag indicating if the component needs a redraw */
    private var needsRedraw = true

    /** Render cache with the last render result */
    private var renderCache: Any? = null

    /** A list of stream unsubscribe functions. Will be call when the component is removed */
    private val unSubscribers = mutableListOf<() -> Unit>()

    /**
     * Every component needs to implement this method
     */
    abstract fun VDom.render()

    /**
     * Every component can react when it is created
     */
    open fun onCreate() {
    }

    /**
     * Every component can react when it is removed
     */
    open fun onRemove() {
    }

    /**
     * Returns 'true' when the component should redraw.
     *
     * By default returns [props] != [nextProps]
     */
    open fun shouldRedraw(nextProps: PROPS): Boolean {
//        console.log("nextProps", this, nextProps, props, nextProps != props)
        return props != nextProps
    }

    /**
     * Triggers a redraw
     */
    fun triggerRedraw() {
        // triggering a redraw for all parent components
        _triggerRedrawRecursive()
    }

    /**
     * Sends a message to all parent components in the tree
     */
    fun sendMessage(message: Message) {
        // We do not dispatch the message on the component that sent it
        if (message.sender != this) {
            events.send(message)
        } else {
            // but we will trigger a redraw
            triggerRedraw()
        }

        // Continue with the parent if the message was not stopped
        _parent?.let {
            if (!message.isStopped) {
                it.sendMessage(message)
            }
        }
    }

    fun onMessage(handler: (Message?) -> Unit) {
        events.stream {
            handler(it)
        }
    }

    ////  Protected helpers  ///////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Subscribes to a [Stream].
     *
     * When the components is destroyed, the subscription will be unsubscribed automatically.
     */
    protected operator fun <T> Stream<T>.invoke(handler: (T) -> Unit): () -> Unit {

        return this.subscribeToStream(handler).apply {
            unSubscribers.add(this)
        }
    }

    /**
     * Subscribes to a [Stream].
     *
     * When the components is destroyed, the subscription will be unsubscribed automatically.
     */
    protected fun <T> Stream<T>.subscribe(handler: (T) -> Unit): () -> Unit = this(handler)

    /**
     * Creates a property that is subscribed to a stream.
     *
     * The property will always contain the current value of the stream.
     * When a new value is received from the stream the component redraws itself.
     *
     * @param stream The stream to subscribe to.
     */
    fun <T> subscribingTo(stream: Stream<T>, onNext: ((T) -> Unit)? = null): ReadOnlyProperty<Any?, T> {

        var first = true

        stream {

            // The first value will be received right away.
            // This can lead to situations where the [onNext] callback tries to use the property right away.
            // But the property is not yet initialized and a very cryptic null pointer errors will arise.
            // To work around this, we hold the first value back, because the property will be initialized properly.
            if (first) {
                first = false

                GlobalScope.launch {
                    // notify about the change and trigger redraw
                    onNext?.invoke(it)
                    // redraw the component
                    triggerRedraw()
                }
            } else {
                // notify about the change and trigger redraw
                onNext?.invoke(it)
                // redraw the component
                triggerRedraw()
            }
        }

        return ReadOnlyProperty { _, _ -> stream() }
    }

    /**
     * Creates a read write property for the components state.
     *
     * When the value of the property is changed the component will redraw itself.
     *
     * @param initial  The initial value of the property
     * @param onChange Callback to be called when the value has changed
     */
    fun <T> value(initial: T, onChange: ((T) -> Unit)? = null): ObservableComponentProperty<T> {

        return ObservableComponentProperty(
            component = this,
            initialValue = initial,
            onChange = onChange
        )
    }

    /**
     * Creates a property that is backed by a [StreamSource]
     *
     * When ever a value is written to the property it will also by passed into the stream.
     * The stream will be configured using [config] and will be then be subscribed to.
     *
     * This way we can implement e.g. debouncing of input values, for example:
     *
     * ```
     * private var search by stream("", { debounce(300) }) { reload() }
     * ```
     *
     * @param initial The initial value
     * @param config  Configures the stream before subscribing to it
     * @param handler Handler for values published by the stream
     */
    fun <T> stream(initial: T, config: (Stream<T>.() -> Stream<T>)? = null, handler: (T) -> Unit = {}): ObservableStreamProperty<T> {

        val stream = StreamSource(initial)

        // Configure the stream, subscribe and invoke the handler with all new values
        val configured = when (config) {
            null -> stream
            else -> stream.config()
        }

        // Subscribe to the configured stream.
        // NOTICE: little hack here with the GlobalScope, to prevent the value being published twice.
        GlobalScope.launch {
            configured { handler(it) }
        }

        return ObservableStreamProperty(component = this, stream = stream)
    }

    ////  Internal functions  //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Internal helper for recursively triggering a redraw on all parent components
     */
    private fun _triggerRedrawRecursive() {
        // optimization
        if (needsRedraw) {
            return
        }

        needsRedraw = true

        // propagate the redraw to the parent
        when (val p = _parent) {
            null -> ctx.engine.triggerRedraw()
            else -> p._triggerRedrawRecursive()
        }
    }

    /**
     * Internal function called by the [VDomEngine] when the component is about to be rendered.
     *
     * The [newCtx] contains the next set of [PROPS]. These are passed to [shouldRedraw].
     */
    internal fun _nextCtx(newCtx: Ctx<PROPS>) {

        if (shouldRedraw(newCtx.props)) {
            needsRedraw = true
        }

        _props = newCtx.props
        _parent = newCtx.parent
    }

    /**
     * Internal function for setting the [dom] element of the component
     */
    internal fun _setDom(dom: HTMLElement?) {
        this._dom = dom
    }

    /**
     * Internal function called by the [VDomEngine] when the component is removed from the DOM.
     *
     * Unsubscribes all async listeners.
     *
     * Calls [onRemove] on the component.
     */
    internal fun _internalOnRemove() {
        // unsubscribe from all stream subscriptions
        unSubscribers.forEach { it() }

        onRemove()
    }

    /**
     * Internal function called by the [VDomEngine] to render the component.
     *
     * This method checks for [needsRedraw].
     * When 'true' then [render] method of the component is called and the result is put into the [renderCache].
     * When 'false' the [render] method is not called and the [renderCache] is returned.
     */
    internal fun _internalRender(): Any? {
//        console.log("needsRedraw", needsRedraw, this)

        if (!needsRedraw) {
            return renderCache
        }

        needsRedraw = false

        @Suppress("UnsafeCastFromDynamic")
        renderCache = ctx.engine.render(this) { render() }

        return renderCache
    }
}

