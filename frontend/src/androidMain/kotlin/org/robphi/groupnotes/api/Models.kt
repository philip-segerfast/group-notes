package org.robphi.groupnotes.api

import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.Date

typealias UserId = Long
typealias GroupId = Long
typealias NoteId = Long

@Serializable
data class Group(
    val id: GroupId,
    val name: String,
    val userId: UserId
)

@Serializable
data class WrappedGroup(
    val userGroup: Group
)

@Serializable
data class CreateGroupResponse(
    val userGroup: Group
)

@Serializable
data class AddMembersRequest(
    val userIdList: List<UserId>,
    val groupId: GroupId
)

@Serializable
data class CreateGroupRequest(
    val name: String,
    val userId: UserId
)

@Serializable
data class StoredGroups(
    val userGroups: List<Group>
)

@Serializable
data class StoredUserResponse(
    val users: List<User>
)

@Serializable
data class User(
    val id: UserId,
    val name: String
)

@Serializable
data class GetUserResponse(
    val user: User?
)

@Serializable
data class GroupRequest(
    val group: Group
)

@Serializable
data class Note(
    val id: NoteId,
    val title: String,
    val content: String,
    val groupId: GroupId,
    val timestamp: String
) {
    companion object {
        fun createDummy(title: String): Note = Note(
            id = 0,
            title = title,
            content = "",
            groupId = 0,
            timestamp = Instant.now().toString()
        )
    }
}

@Serializable
data class NoteList(
    val notes: List<Note>
)