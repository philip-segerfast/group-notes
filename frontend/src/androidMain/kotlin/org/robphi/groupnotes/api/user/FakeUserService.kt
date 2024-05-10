package org.robphi.groupnotes.api.user

import org.robphi.groupnotes.api.GetUserResponse
import org.robphi.groupnotes.api.StoredUserResponse
import org.robphi.groupnotes.api.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeUserService : UserService {

    private var userIdCounter = 0L

    private val _users = MutableStateFlow(
        listOf(
            User(userIdCounter++, "Kevin"),
            User(userIdCounter++, "Albin"),
            User(userIdCounter++, "Hanna"),
            User(userIdCounter++, "Gustav"),
            User(userIdCounter++, "Anna"),
            User(userIdCounter++, "Pelle"),
            User(userIdCounter++, "Nils"),
            User(userIdCounter++, "Jakob"),
            User(userIdCounter++, "Emma"),
        )
    )

    override suspend fun getUserById(id: Long): Result<GetUserResponse> = runCatching {
        GetUserResponse(_users.value.find { it.id == id })
    }

    override suspend fun getAllUsers(): Result<StoredUserResponse> = runCatching {
        StoredUserResponse(_users.value)
    }

    override suspend fun createUser(name: String): Result<Unit> {
        _users.update { it + User(userIdCounter++, name) }
        return Result.success(Unit)
    }

    override suspend fun updateUser(): Result<Unit> {
        TODO("Undefined behaviour")
    }
}