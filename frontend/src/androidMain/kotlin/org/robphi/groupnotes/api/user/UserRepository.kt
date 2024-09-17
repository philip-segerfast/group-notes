package org.robphi.groupnotes.api.user

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull
import org.robphi.groupnotes.api.User
import org.robphi.groupnotes.auth.Auth
import org.robphi.groupnotes.exception.ClientNotAuthenticatedException
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class UserRepository(
    private val userService: UserService,
    private val auth: Auth
) {
    suspend fun createUser(name: String): Result<Unit> = userService.createUser(name)

    suspend fun updateUser(): Result<Unit> = TODO()

    suspend fun getAllUsers(): Result<List<User>> = userService.getAllUsers().map { it.users }

    suspend fun getUserById(userId: Long): Result<User?> = userService.getUserById(userId).map { it.user }

    /** Get client user. If not authenticated within 3 seconds it will return Result.failure.  */
    suspend fun getClientUser(timeout: Duration = 5.seconds): Result<User?> = kotlin.runCatching {
        val clientUserId = withTimeout(timeout) { auth.userId.first { it != null } }
        clientUserId ?: return Result.failure(ClientNotAuthenticatedException())
        return userService.getUserById(clientUserId).map { it.user }
    }

}