package com.example.groupnotes.api

import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// Has access to database. Perform DB operations.
interface GroupService {
    @GET("group/getById/{id}")
    suspend fun getGroupById(@Path("id") id: Long): Group

    @GET("group/get_all")
    suspend fun getAllGroups(): StoredGroups

    @POST("group/create")
    suspend fun createGroup(name: String, userId: Long)

    suspend fun addMembers(userId: Int, groupId: Long, members: List<String>)
}

@Serializable
data class Group(
    val id: Long,
    val name: String,
    val userId: Long
)

@Serializable
data class StoredGroups(
    val userGroups: List<Group>
)
