package robphi.server.ktor.repo

import app.cash.sqldelight.Query
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

fun <T: Any, R: Any> Query<T>.transformAsListFlow(transform: (T) -> R): Flow<List<R>> = asFlow()
    .distinctUntilChanged()
    .map { it.awaitAsList().map(transform) }
    .distinctUntilChanged()

fun <T: Any, R: Any> Query<T>.transformAsOneFlow(transform: (T) -> R): Flow<R> = asFlow()
    .distinctUntilChanged()
    .map { it.awaitAsOne().let(transform) }
    .distinctUntilChanged()

suspend fun <T: Any, R: Any> Query<T>.transformAsList(transform: (T) -> R): List<R> =
    awaitAsList().map(transform)

suspend fun <T: Any, R: Any> Query<T>.transformAsSingle(transform: (T) -> R): R =
    awaitAsOne().let(transform)
