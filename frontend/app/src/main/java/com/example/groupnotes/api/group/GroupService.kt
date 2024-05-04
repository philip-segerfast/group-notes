package com.example.groupnotes.api.group

import com.example.groupnotes.api.Group
import com.example.groupnotes.api.StoredGroups
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GroupService {

    @GET("group/getById/{id}")
    suspend fun getGroupById(@Path("id") id: Long): Result<Group>

    @GET("group/get_all")
    suspend fun getAllGroups(): Result<StoredGroups>

    @POST("group/create")
    suspend fun createGroup(name: String, userId: Long): Response<String>

    @DELETE("group/delete/{groupId}")
    suspend fun deleteGroup(groupId: Long)

    // TODO - add to backend
    @PUT("group/addmembers")
    suspend fun addMembers(userId: Long, groupId: Long, newMembers: List<Long>)

}