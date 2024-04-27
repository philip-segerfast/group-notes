package com.example.groupnotes.api

import kotlinx.serialization.Serializable
import retrofit2.http.GET

val groupRepository by lazy { GroupRepository(groupService) }

// Has access to database. Perform DB operations.
interface GroupService {
    @GET("group/getById/{id}")
    suspend fun getGroupById(id: Long)

    @GET("group/get_all")
    suspend fun getAllGroups(): StoredGroups
}

class GroupRepository(private val groupService: GroupService) {
    suspend fun getGroupById(id: Long) = groupService.getGroupById(id)
    suspend fun getAllGroups() = groupService.getAllGroups().userGroups
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
