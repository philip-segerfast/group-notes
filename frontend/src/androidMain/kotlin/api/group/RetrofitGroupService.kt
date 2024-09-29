package api.group

import model.AddMembersRequest
import model.CreateGroupRequest
import model.CreateGroupResponse
import model.StoredGroups
import model.UserId
import model.WrappedGroup
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitGroupService : GroupService {

    @GET("group/getById/{groupId}")
    override suspend fun getGroupById(@Path("groupId") id: Long): Result<WrappedGroup>

    @GET("group/get_all")
    override suspend fun getAllGroups(): Result<StoredGroups>

    @GET("group/get_all/{userId}")
    override suspend fun getGroupsForUser(@Path("userId") userId: UserId): Result<List<WrappedGroup>>

    @POST("group/create")
    override suspend fun createGroup(@Body body: CreateGroupRequest): Result<CreateGroupResponse>

    @DELETE("group/delete/{groupId}")
    override suspend fun deleteGroup(@Path("groupId") groupId: Long)

    // TODO - add to backend
    @POST("member/create")
    override suspend fun addMembers(@Body request: AddMembersRequest)

}