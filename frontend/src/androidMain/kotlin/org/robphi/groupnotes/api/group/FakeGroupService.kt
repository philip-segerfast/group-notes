package com.example.groupnotes.api.group

import com.example.groupnotes.api.Group
import com.example.groupnotes.api.StoredGroups
import com.example.groupnotes.api.User
import com.example.groupnotes.api.user.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.random.Random

private typealias GroupId = Long
private typealias UserId = Long

class FakeGroupService(
    private val userRepository: UserRepository
) : GroupService {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val seed = 10

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
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
        val allUsers = userRepository.getAllUsers().getOrElse { return }.users
        val random = Random(seed)

        val groups = createFakeGroups(10, allUsers, random)
        val groupMembers = createFakeGroupMembers(groups, allUsers, random)
        _groups.value = groups
        _groupMembers.value = groupMembers
    }

    override suspend fun getGroupById(id: Long): Result<Group> = runCatching {
        _groups.value.first { it.id == id }
    }

    override suspend fun getAllGroups(): Result<StoredGroups> = Result.success(StoredGroups(_groups.first { it.isNotEmpty() }))

    override suspend fun createGroup(name: String, userId: Long): Response<String> {
        val current = _groups.value
        val newGroup = Group(currentGroupId++, name, userId)
        _groups.value = current + newGroup
        return Response.success(newGroup.id.toString())
    }

    override suspend fun deleteGroup(groupId: Long) {
        val groupToDelete = _groups.value.first { it.id == groupId }
        _groups.update { it - groupToDelete }
    }

    override suspend fun addMembers(userId: Long, groupId: Long, newMembers: List<Long>) {
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

        fun createFakeGroups(
            count: Int,
            users: List<User>,
            random: Random
        ): List<Group> = List(count) {
            Group(currentGroupId++, "Group #$currentGroupId", users.random(random).id)
        }

        fun createFakeGroupMembers(
            groups: List<Group>,
            users: List<User>,
            random: Random
        ): Map<GroupId, List<UserId>> = groups.associateWith { group ->
            val membersCount = random.nextInt(MIN_MEMBERS_PER_GROUP, MAX_MEMBERS_PER_GROUP)
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