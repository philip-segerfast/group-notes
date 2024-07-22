package org.robphi.groupnotes.api.user

import org.robphi.groupnotes.api.GetUserResponse
import org.robphi.groupnotes.api.StoredUserResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("/user/create/{name}")
    suspend fun createUser(name: String): Result<Unit>

    @PATCH("/user/update/{id}")
    suspend fun updateUser(): Result<Unit>

    @GET("/user/getUsers")
    suspend fun getAllUsers(): Result<StoredUserResponse>

    @GET("/user/getUser/{id}")
    suspend fun getUserById(@Path("id") id: Long): Result<GetUserResponse>

}