package de.peekandpoke.ktorfx.upnext.backend

import de.peekandpoke.ktorfx.upnext.shared.WorkflowDescription


fun <S, B : S> createWorkflowBackend(
    workflow: WorkflowDescription<S>,
    block: WorkflowBackendBuilder<S, B>.() -> Unit
): WorkflowBackend<B> =
    WorkflowBackendBuilder<S, B>(workflow).apply(block).build()


