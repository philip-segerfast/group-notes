package org.robphi.groupnotes.api.user

import org.robphi.groupnotes.auth.Auth

class UserRepository(
    private val userService: UserService,
    private val auth: Auth
) {

    suspend fun createUser(name: String) = userService.createUser(name)

    suspend fun updateUser(): Result<Unit> = TODO()

    suspend fun getAllUsers() = userService.getAllUsers()

    suspend fun getUserById(userId: Long) = userService.getUserById(userId)

    suspend fun getClientUser() = userService.getUserById(auth.userId.value!!)

}