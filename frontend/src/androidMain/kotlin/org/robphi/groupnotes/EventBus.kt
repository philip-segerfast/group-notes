package org.robphi.groupnotes

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest

object EventBus {
    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    suspend inline fun <reified T: Event> subscribe(noinline block: suspend (T) -> Unit) {
        events.collectLatest { event ->
            if(event is T) {
                block(event)
            }
        }
    }
}

interface Event

data object ClientSignedInEvent: Event
data object FakeUserDataInitialized: Event
