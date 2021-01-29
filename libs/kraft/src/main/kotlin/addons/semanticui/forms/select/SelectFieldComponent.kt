package de.peekandpoke.kraft.addons.semanticui.forms.select

import de.peekandpoke.kraft.addons.forms.FormFieldComponent
import de.peekandpoke.kraft.addons.semanticui.forms.renderErrors
import de.peekandpoke.kraft.addons.validation.Rule
import de.peekandpoke.kraft.components.*
import de.peekandpoke.kraft.store.addons.debounce
import de.peekandpoke.kraft.vdom.VDom
import de.peekandpoke.ultra.semanticui.*
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.css.CSSBuilder
import kotlinx.html.*
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event

typealias OptionFilterFn<T> = List<SelectFieldComponent.Option<T>>.(search: String) -> List<SelectFieldComponent.Option<T>>

typealias AutoSuggestFn<T> = suspend (search: String) -> Flow<List<SelectFieldComponent.Option<T>>>

class SelectFieldComponent<T>(ctx: Ctx<Props<T>>) : FormFieldComponent<T, SelectFieldComponent.Props<T>>(ctx) {

    data class Props<P>(
        val config: Config<P>,
    ) : FormFieldComponent.Props<P> {
        override val initialValue: P get() = config.value
        override val fromStr: (String) -> P get() = config.fromStr
        override val onChange: (P) -> Unit get() = config.onChange
        override val rules: List<Rule<P>> get() = config.rules
    }

    data class Option<T>(
        val realValue: T,
        val formValue: String,
        val display: HTMLTag.() -> Unit
    )

    class Config<T>(
        var value: T,
        val onChange: (T) -> Unit,
    ) {
        val asProps get() = Props(config = this)

        /** The validation rules */
        val rules: MutableList<Rule<T>> = mutableListOf()

        /** The options of the select field */
        val options: MutableList<Option<T>> = mutableListOf()

        /** Converts a string back to the real value of an option */
        val fromStr: (str: String) -> T = { str -> options.first { it.formValue == str }.realValue }

        /** The label renderer of the field */
        var label: LABEL.() -> Unit = {}
            private set

        /** Renderer for a placeholder option */
        var placeholder: (HTMLTag.() -> Unit)? = null
            private set

        /** The css set for the select field */
        var css: CSSBuilder? = null
            private set

        /** When true the field is rendered inverted */
        var inverted: Boolean = false
            private set

        /** When set, the field will be rendered as a searchable field */
        var searchableBy: OptionFilterFn<T>? = null
            private set

        /** Wheb set, the field will be rendered as a searchable auto-suggest field */
        var autoSuggest: AutoSuggestFn<T>? = null
            private set

        /** The label renderer of the field */
        private var _label: LABEL.() -> Unit = {}

        /**
         * Sets the label
         */
        fun label(label: String) = apply {
            _label = { +label }
        }

        /**
         * Sets the label as a render function
         */
        fun label(rendered: LABEL.() -> Unit) = apply {
            _label = rendered
        }

        /**
         * Sets the placeholder
         */
        fun placeholder(placeholder: String) {
            placeholder { +placeholder }
        }

        /**
         * Sets the placeholder
         */
        fun placeholder(placeholder: HTMLTag.() -> Unit) {
            this.placeholder = placeholder
        }

        /**
         * Sets the css the select field
         */
        fun css(block: CSSBuilder.() -> Unit) {
            this.css = CSSBuilder().apply(block)
        }

        /**
         * Sets the style to be inverted or not.
         */
        fun inverted(inverted: Boolean = true) {
            this.inverted = inverted
        }

        /**
         * Sets the filter by which the options are filtered
         */
        fun searchableBy(filter: OptionFilterFn<T>) {
            this.searchableBy = filter
        }

        /**
         * Sets the callback that provides auto-suggest options
         */
        fun autoSuggest(suggest: AutoSuggestFn<T>) {
            this.autoSuggest = suggest
        }

        /**
         * Adds a validation rule
         */
        fun accepts(rule: Rule<T>, vararg rules: Rule<T>) {
            this.rules.add(rule)
            this.rules.addAll(rules)
        }

        /**
         * Adds an option
         */
        fun option(realValue: T, formValue: String, display: String) {
            option(realValue = realValue, formValue = formValue) { +display }
        }

        /**
         * Adds an option
         */
        fun option(realValue: T, formValue: String, display: HTMLTag.() -> Unit) {
            options.add(
                Option(realValue = realValue, formValue = formValue, display = display)
            )
        }

        /**
         * Adds an option
         *
         * In this case the [formValue] will also be used as the display
         */
        fun option(realValue: T, formValue: String) =
            option(realValue = realValue, formValue = formValue) { +formValue }

        /**
         * Adds an option
         *
         * In this case the [realValue] will be used as formValue
         */
        fun option(realValue: T, display: HTMLTag.() -> Unit) {
            options.add(
                Option(realValue = realValue, formValue = realValue.toString(), display = display)
            )
        }
    }

    ////  STATE ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private val ctrl by lazy { SelectFieldController(this) }

    private var selectedOption: Option<T>? by value(
        props.config.options.firstOrNull { it.realValue == props.config.value }
    )

    private var autoSuggestOptions: List<Option<T>> by value(emptyList())

    private var search: String by stream("", { debounce(300) }) { newSearch ->
        // When this is an auto-suggest field we query the new options
        props.config.autoSuggest?.let { autoSuggest ->
            GlobalScope.launch {
                autoSuggest(newSearch).collect { result ->
                    autoSuggestOptions = result

                    if (selectedOption == null) {
                        selectedOption = result.firstOrNull { it.realValue == props.config.value }
                    }
                }
            }
        }
    }

    private var isSearchFocused by value(false)

    ////  LIFE-CYCLE  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun onCreate() {
        super.onCreate()

        // Add a mouse down handler on the document.
        // By doing so we can close the dropdown when the user clicks somewhere else on the page.
        window.document.addEventListener(type = "mousedown", callback = ::onDocumentMouseDown)
    }

    override fun onRemove() {
        super.onRemove()

        // Remove the mouse down handler.
        window.document.removeEventListener(type = "mousedown", callback = ::onDocumentMouseDown)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun onDocumentMouseDown(event: Event) {
        search = ""
        ctrl.close()
    }

    ////  RENDERING  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    override fun VDom.render() {
        ui.given(hasErrors) { error }.field {

            // Prevent the event from bubbling up to the document
            onMouseDown { event ->
                event.stopImmediatePropagation()
                event.preventDefault()
            }

            renderField()
            renderErrors(this@SelectFieldComponent, this)
        }
    }

    private fun FlowContent.renderField() {

        // Is this field searchable?
        val searchableBy = props.config.searchableBy
        // Is this an auto-suggest field?
        val autoSuggest = props.config.autoSuggest

        when {
            searchableBy != null -> renderSearchableField(options = searchableBy(props.config.options, search))

            autoSuggest != null -> renderSearchableField(options = autoSuggestOptions)

            else -> renderDefaultField(options = props.config.options)
        }
    }

    private fun FlowContent.renderDefaultField(options: List<Option<T>>) {

        ui.given(props.config.inverted) { inverted }
            .let { ctrl.applyStateOnField(it) }
            .selection.dropdown {
                onClick { ctrl.toggleState() }

                icon.dropdown()

                when (selectedOption) {
                    // When nothing is selected we show the placeholder if there is any.
                    null -> props.config.placeholder?.let { placeholder -> noui.default.text { placeholder(this) } }
                    // Otherwise we show the selected option
                    else -> noui.text { selectedOption!!.display(this) }
                }

                // render options
                renderOptions(options = options) { option ->
                    chooseOption(option)
                }
            }
    }

    private fun FlowContent.renderSearchableField(options: List<Option<T>>) {

        ui.given(props.config.inverted) { inverted }
            .let { ctrl.applyStateOnField(it) }
            .search.selection.dropdown {
                onClick { ctrl.toggleState() }

                icon.dropdown()

                // is the field searchable
                input(classes = "search") {
                    value = search
                    // Prevent the mouse event from bubbling up, as this would close/toggle the dropdown
                    onMouseDown { evt -> evt.stopImmediatePropagation() }
                    // Watch for inputs
                    onInput { evt -> search = (evt.target as HTMLInputElement).value }
                    // Track if the input field gets the focus
                    onFocus { isSearchFocused = true }
                    // Track if the input field looses the focus
                    onBlur { isSearchFocused = false }
                }

                // Render the placeholder
                val valueDisplay: (FlowContent.() -> Unit) = when {
                    // When the search is not blank we only show the search input
                    search.isNotBlank() -> flowContent { }
                    // Otherwise if there is a selected option we show it
                    selectedOption != null -> when (isSearchFocused) {
                        false -> flowContent { noui.text { selectedOption!!.display(this) } }
                        // When the search input is focused we display the selected option like a placeholder
                        true -> flowContent { noui.default.text { selectedOption!!.display(this) } }
                    }
                    // Otherwise we show the placeholder
                    else -> flowContent {
                        props.config.placeholder?.let { p -> noui.default.text { p(this) } }
                    }
                }

                // Render the placeholder
                valueDisplay(this)

                // render options
                renderOptions(options = options) { option ->
                    chooseOption(option)
                    search = ""
                }
            }
    }

    private fun chooseOption(option: Option<T>) {
        selectedOption = option
        setValue(option.realValue)
    }

    private fun FlowContent.renderOptions(
        options: List<Option<T>>,
        onSelect: (Option<T>) -> Unit,
    ) {
        // render the option
        noui
            .let { ctrl.applyStateOnOptions(it) }
            .menu {
                // Wait for the animation to end so we can advance the state
                onAnimationEnd { ctrl.advanceState() }

                options.forEach { option ->
                    noui.given(option.realValue == selectedOption?.realValue) { this.selected }.item {
                        option.display(this)
                        onClick {
                            onSelect(option)
                        }
                    }
                }
            }
    }
}
