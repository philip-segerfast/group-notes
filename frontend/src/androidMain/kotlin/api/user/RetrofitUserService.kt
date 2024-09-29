package api.user

import model.GetUserResponse
import model.StoredUserResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitUserService : UserService {

    @POST("/user/create/{name}")
    override suspend fun createUser(name: String): Result<Unit>

    @PATCH("/user/update/{id}")
    override suspend fun updateUser(): Result<Unit>

    @GET("/user/getUsers")
    override suspend fun getAllUsers(): Result<StoredUserResponse>

    @GET("/user/getUser/{id}")
    override suspend fun getUserById(@Path("id") id: Long): Result<GetUserResponse>

}