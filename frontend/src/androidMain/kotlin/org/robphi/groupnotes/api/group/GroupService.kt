package org.robphi.groupnotes.api.group

import org.robphi.groupnotes.api.AddMembersRequest
import org.robphi.groupnotes.api.CreateGroupRequest
import org.robphi.groupnotes.api.CreateGroupResponse
import org.robphi.groupnotes.api.StoredGroups
import org.robphi.groupnotes.api.UserId
import org.robphi.groupnotes.api.WrappedGroup
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GroupService {

    @GET("group/getById/{groupId}")
    suspend fun getGroupById(@Path("groupId") id: Long): Result<WrappedGroup>

    @GET("group/get_all")
    suspend fun getAllGroups(): Result<StoredGroups>

    @GET("group/get_all/{userId}")
    suspend fun getGroupsForUser(@Path("userId") userId: UserId): Result<List<WrappedGroup>>

    @POST("group/create")
    suspend fun createGroup(@Body body: CreateGroupRequest): Response<CreateGroupResponse>

    @DELETE("group/delete/{groupId}")
    suspend fun deleteGroup(@Path("groupId") groupId: Long)

    // TODO - add to backend
    @POST("member/create")
    suspend fun addMembers(@Body request: AddMembersRequest)

}