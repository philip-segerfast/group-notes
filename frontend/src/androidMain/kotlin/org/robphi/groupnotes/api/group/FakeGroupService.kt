package org.robphi.groupnotes.api.group

import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.StoredGroups
import org.robphi.groupnotes.api.User
import org.robphi.groupnotes.api.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.robphi.groupnotes.api.AddMembersRequest
import org.robphi.groupnotes.api.CreateGroupRequest
import org.robphi.groupnotes.api.CreateGroupResponse
import org.robphi.groupnotes.api.GroupId
import org.robphi.groupnotes.api.UserId
import org.robphi.groupnotes.api.WrappedGroup
import org.robphi.groupnotes.util.createDummy
import retrofit2.Response
import kotlin.random.Random

class FakeGroupService(
    private val userRepository: UserRepository
) : GroupService {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val seed = 10

    private val _groups = MutableStateFlow(emptyList<Group>())
    private val _groupMembers = MutableStateFlow<Map<GroupId, List<UserId>>>(emptyMap())
    val groupsWithMembers = _groups.combine(_groupMembers) { groups, members ->
        groups.associateWith { group -> members[group.id]!! }
    }

    init {
        scope.launch {
            setFakeData()
        }
    }

    private suspend fun setFakeData() {
        val allUsers = userRepository.getAllUsers().getOrThrow()
        val random = Random(seed)

        val groups = createFakeClientGroups()
        _groups.value = groups

        val groupMembers = createFakeGroupMembers(groups, allUsers, random)
        _groupMembers.value = groupMembers
    }

    private suspend fun createFakeClientGroups(): List<Group> = runBlocking {
        val clientUser = userRepository.getClientUser().getOrThrow() ?: error("Client user not found.")
        listOf("Family", "School", "Friends", "Work", "Random", "Other", "${clientUser.name}'s secrets").mapIndexed { index, groupName ->
            Group.createDummy(groupName, index.toLong(), clientUser.id)
        }
    }

    override suspend fun getGroupsForUser(userId: UserId): Result<List<WrappedGroup>> =
        _groups.value
            .filter { it.userId == userId }
            .map { WrappedGroup(it) }
            .let { Result.success(it) }

    override suspend fun getGroupById(id: Long): Result<WrappedGroup> = runCatching {
        WrappedGroup(_groups.value.first { it.id == id })
    }

    override suspend fun getAllGroups(): Result<StoredGroups> = Result.success(StoredGroups(_groups.first { it.isNotEmpty() }))

    override suspend fun createGroup(body: CreateGroupRequest): Response<CreateGroupResponse> {
        val current = _groups.value
        val group = Group(Random.nextLong(), body.name, body.userId)
        _groups.value = current + group
        return Response.success(CreateGroupResponse(group))
    }

    override suspend fun deleteGroup(groupId: Long) {
        val groupToDelete = _groups.value.first { it.id == groupId }
        _groups.update { it - groupToDelete }
    }

    override suspend fun addMembers(request: AddMembersRequest) {
        val (newMembers, groupId) = request
        val groupsWithMembers = _groupMembers.value
        val currentMembers = groupsWithMembers[groupId] ?: error("Invalid groupId: $groupId")
        val combinedMembers = (currentMembers + newMembers).distinct() // Use Set to remove duplicates
        val newGroupsWithMembers = groupsWithMembers + (groupId to combinedMembers)
        _groupMembers.value = newGroupsWithMembers
    }

    private companion object {
        private var currentGroupId: Long = 0
        private const val MIN_MEMBERS_PER_GROUP = 1
        private const val MAX_MEMBERS_PER_GROUP = 6

        fun createFakeGroupMembers(
            clientGroups: List<Group>,
            users: List<User>,
            random: Random
        ): Map<GroupId, List<UserId>> {
            println(
                buildString {
                    appendLine("Creating fake group members.")
                    val clientGroupsBulletList = clientGroups
                        .takeIf { it.isNotEmpty() }
                        ?.joinToString("") { "${ System.lineSeparator() }* $it" }
                        ?: "None"
                    appendLine("Client groups: $clientGroupsBulletList")
                    appendLine("Users: $users")
                }
            )

            return clientGroups.associateWith { group ->
                val membersCount = random.nextInt(MIN_MEMBERS_PER_GROUP, MAX_MEMBERS_PER_GROUP)
                println("Group ${group.name} will have $membersCount members.")
                val usersExceptGroupCreator = users.filter { it.id != group.userId }

                // Get random members
                val members: List<User> = (0..membersCount).fold(emptyList()) { members, _ ->
                    val remainingUsers = usersExceptGroupCreator - members.toSet()
                    members + remainingUsers.random(random)
                }
                members
            }.map { (group, members) ->
                group.id to members.map { it.id }
            }.toMap()
        }
    }
}