package robphi.server.ktor.repo

import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.coroutines.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.koin.dsl.module
import robphi.server.ktor.database.UserQueries
import robphi.server.ktor.model.UserModel
import robphi.server.ktor.model.toModel

val userModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }
}

interface UserRepository {

    suspend fun getUsers(): List<UserModel>

    suspend fun getUsersAsFlow(): Flow<List<UserModel>>

    suspend fun getUser(uid: Int): UserModel

    suspend fun getUserAsFlow(id: Int): Flow<UserModel>

    suspend fun createUser(name: String): UserModel

    suspend fun updateUser(user: UserModel): Boolean

}

class UserRepositoryImpl(private val userQueries: UserQueries) : UserRepository {
    override suspend fun createUser(name: String): UserModel = userQueries
        .insert(name)
        .awaitAsOne()
        .toModel()

    override suspend fun updateUser(user: UserModel): Boolean = userQueries
        .update(user.name, user.id)
        .awaitAsOne()
        .let { true }

    override suspend fun getUsersAsFlow(): Flow<List<UserModel>> = userQueries
        .selectAll()
        .asFlow()
        .distinctUntilChanged()
        .map { query -> query.awaitAsList().map { it.toModel() } }

    override suspend fun getUserAsFlow(id: Int): Flow<UserModel> = userQueries
        .selectById(id)
        .asFlow()
        .distinctUntilChanged()
        .map { query -> query.awaitAsOne().toModel() }

    override suspend fun getUsers(): List<UserModel> = userQueries
        .selectAll()
        .awaitAsList()
        .map { it.toModel() }

    override suspend fun getUser(uid: Int): UserModel = userQueries
        .selectById(uid)
        .awaitAsOne()
        .toModel()
}
