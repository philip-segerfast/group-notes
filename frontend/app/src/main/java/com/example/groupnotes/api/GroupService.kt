package com.example.groupnotes.api

import kotlinx.serialization.Serializable
import retrofit2.http.GET

val groupRepository by lazy { GroupRepository(groupService) }

// Has access to database. Perform DB operations.
interface GroupService {
    @GET("group/{id}")
    fun getGroupById(id: Long)

    @GET("group/get_all")
    fun getAllGroups()
}

class GroupRepository(private val groupService: GroupService) {
    fun getGroupById(id: Long) = groupService.getGroupById(id)
    fun getAllGroups() = groupService.getAllGroups()
}

@Serializable
data class Group(
    val id: Long,
    val name: String,
    val userId: Int
)

@Serializable
data class StoredGroups(
    val groups: List<Group>
)
