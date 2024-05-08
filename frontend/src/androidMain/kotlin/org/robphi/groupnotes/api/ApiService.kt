package com.example.groupnotes.api

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val id: Long,
    val name: String,
    val userId: Long
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
    val id: Long,
    val name: String
)

@Serializable
data class GetUserResponse(
    val user: User?
)