package com.example.groupnotes.api.user

import com.example.groupnotes.auth.Auth

class UserRepository(
    private val userService: UserService,
    private val auth: Auth
) {

    suspend fun getUserById(userId: Long) = userService.getUserById(userId)

    suspend fun getAllUsers() = userService.getAllUsers()

    suspend fun getClientUser() = userService.getUserById(auth.userId.value!!)

}