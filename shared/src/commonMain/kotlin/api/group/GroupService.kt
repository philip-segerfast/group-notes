package api.group

import model.AddMembersRequest
import model.CreateGroupRequest
import model.CreateGroupResponse
import model.StoredGroups
import model.UserId
import model.WrappedGroup

interface GroupService {

    suspend fun getGroupById(id: Long): Result<WrappedGroup>

    suspend fun getAllGroups(): Result<StoredGroups>

    suspend fun getGroupsForUser(userId: UserId): Result<List<WrappedGroup>>

    suspend fun createGroup(body: CreateGroupRequest): Result<CreateGroupResponse>

    suspend fun deleteGroup(groupId: Long)

    // TODO - add to backend
    suspend fun addMembers(request: AddMembersRequest)

}
