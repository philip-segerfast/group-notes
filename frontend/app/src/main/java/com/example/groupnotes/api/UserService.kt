package com.example.groupnotes.api

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("user/getUser/{id}")
    suspend fun getUserById(@Path("id") id: Long): GetUserResponse

    @GET("user/getUsers")
    suspend fun getAllUsers(): StoredUserResponse
}

@Serializable
data class StoredUserResponse(
    val users: List<User>
)

@Serializable
data class User(
    val id: Long,
    val name: String
)

@Serializable
data class GetUserResponse(
    val user: User
)