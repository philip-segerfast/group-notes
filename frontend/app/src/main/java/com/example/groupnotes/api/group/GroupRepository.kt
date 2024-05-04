package com.example.groupnotes.api.group

import com.example.groupnotes.auth.Auth

class GroupRepository(
    private val groupService: GroupService,
    private val auth: Auth
) {

    suspend fun getGroupById(id: Long) = groupService.getGroupById(id)

    suspend fun getAllGroups() = groupService.getAllGroups()

    suspend fun createGroup(name: String) = groupService.createGroup(name, auth.userId.value!!)

    suspend fun deleteGroup(id: Long) = groupService.deleteGroup(id)

    suspend fun addMembers(groupId: Long, members: List<Long>) = groupService.addMembers(auth.userId.value!!, groupId, members)

}