package di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

private val _koinStarted = MutableStateFlow(false)
val koinStarted = _koinStarted.asStateFlow()

fun startKoin(block: KoinApplication.() -> Unit) {
    startKoin {
        block()
        _koinStarted.value = true
    }
}
