package api.group

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import model.AddMembersRequest
import model.CreateGroupRequest
import model.Group
import model.GroupId
import model.UserId
import auth.Auth

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
        createGroupResponse.onFailure { error ->
            return Result.failure(Exception("Failed to create group: ${ error.message }"))
        }

        println("Created group: $createGroupResponse")

        val group = createGroupResponse.getOrThrow().userGroup
        val request = AddMembersRequest(members, group.id)
        println("Adding members...")
        groupService.addMembers(request)
        println("Members added!")
        return Result.success(group)
    }

    suspend fun deleteGroup(id: GroupId) = groupService.deleteGroup(id)

    suspend fun addMembers(groupId: GroupId, members: List<UserId>) = groupService.addMembers(
        AddMembersRequest(members, groupId)
    )

}