package org.robphi.groupnotes.api.group

import org.robphi.groupnotes.api.group.GroupService
import org.robphi.groupnotes.auth.Auth

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