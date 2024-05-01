package com.example.groupnotes.api.group

import com.example.groupnotes.auth.Auth

class GroupRepository(
    private val groupService: GroupService,
    private val auth: Auth
) {

    suspend fun getAllGroups() = groupService.getAllGroups()

    suspend fun getGroupById(id: Long) = groupService.getGroupById(id)

    suspend fun createGroup(name: String) = groupService.createGroup(name, auth.userId.value!!)

    suspend fun addMembers(groupId: Long, members: List<String>) = groupService.addMembers(auth.userId.value!!, groupId, members)

}