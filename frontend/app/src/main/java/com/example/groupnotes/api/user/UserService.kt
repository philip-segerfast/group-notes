package com.example.groupnotes.api.user

import com.example.groupnotes.api.GetUserResponse
import com.example.groupnotes.api.StoredUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {

    @GET("user/getUser/{id}")
    suspend fun getUserById(@Path("id") id: Long): Result<GetUserResponse>

    @GET("user/getUsers")
    suspend fun getAllUsers(): Result<StoredUserResponse>

}