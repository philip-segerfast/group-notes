package api.user

import model.GetUserResponse
import model.StoredUserResponse

interface UserService {
    suspend fun createUser(name: String): Result<Unit>

    suspend fun updateUser(): Result<Unit>

    suspend fun getAllUsers(): Result<StoredUserResponse>

    suspend fun getUserById(id: Long): Result<GetUserResponse>
}