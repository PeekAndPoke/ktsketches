package de.peekandpoke.ultra.semanticui

import kotlinx.html.FlowContent

@SemanticUiDslMarker
val FlowContent.ui
    get() = SemanticTag(this, mutableListOf("ui"))

@SemanticUiDslMarker
val FlowContent.noui
    get() = SemanticTag(this, mutableListOf(""))

@SemanticIconMarker
val FlowContent.icon
    get() = SemanticIcon(this)

@SemanticIconMarker
val FlowContent.emoji
    get() = SemanticEmoji(this)

/**
 * Helps the compiler to identify a code block that is supposed to run on a [FlowContent]
 */
fun flowContent(block: FlowContent.() -> Unit) = block

/**
 * Helps the compiler to identify a code block that is supposed to run on a semantic tag
 */
fun semantic(block: SemanticTag.() -> SemanticTag) = block
