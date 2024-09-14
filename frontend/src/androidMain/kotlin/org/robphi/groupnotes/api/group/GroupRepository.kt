package org.robphi.groupnotes.api.group

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.robphi.groupnotes.api.AddMembersRequest
import org.robphi.groupnotes.api.CreateGroupRequest
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.GroupId
import org.robphi.groupnotes.api.UserId
import org.robphi.groupnotes.auth.Auth
import retrofit2.Response

class GroupRepository(
    private val groupService: GroupService,
    private val auth: Auth
) {

    suspend fun getGroupById(id: GroupId): Result<Group> = groupService.getGroupById(id).map { it.userGroup }

    suspend fun getAllGroups() = groupService.getAllGroups()

    suspend fun getAllUserGroups(): Result<List<Group>> = runBlocking {
        auth.userId.first { it != null }
        groupService.getGroupsForUser(auth.userId.value!!).map { it.map { it.userGroup } }
    }

    suspend fun createGroup(name: String, members: List<UserId>): Result<Group> {
        val createGroupRequest = CreateGroupRequest(
            name = name,
            userId = auth.userId.value!!
        )
        val createGroupResponse = groupService.createGroup(createGroupRequest)
        if(!createGroupResponse.isSuccessful)
            return Result.failure(Exception("Failed to create group: ${ createGroupResponse.message() }"))

        println("Created group: $createGroupResponse")

        val group = createGroupResponse.body()!!.userGroup
        val request = AddMembersRequest(members, group.id)
        println("Adding members...")
        groupService.addMembers(request)
        println("Members added!")
        return Result.success(group)
    }

    suspend fun deleteGroup(id: GroupId) = groupService.deleteGroup(id)

    suspend fun addMembers(groupId: GroupId, members: List<UserId>) = groupService.addMembers(AddMembersRequest(members, groupId))

}