package robphi.server.ktor.repo

import app.cash.sqldelight.async.coroutines.awaitAsOne
import kotlinx.coroutines.flow.Flow
import robphi.server.ktor.database.GroupQueries
import robphi.server.ktor.model.GroupId
import robphi.server.ktor.model.GroupModel
import robphi.server.ktor.model.UserId
import robphi.server.ktor.model.toModel

interface GroupRepository {

    // Create
    suspend fun createGroup(name: String, creator: Int): GroupModel

    // Delete
    suspend fun deleteGroup(groupId: GroupId): Boolean

    // Get
    suspend fun getGroups(): List<GroupModel>

    suspend fun getGroupsAsFlow(): Flow<List<GroupModel>>

    suspend fun getGroupsForUser(uid: UserId): List<GroupModel>

    suspend fun getGroupsForUserAsFlow(uid: UserId): Flow<List<GroupModel>>

    suspend fun getGroup(groupId: GroupId): GroupModel

    suspend fun getGroupAsFlow(groupId: GroupId): Flow<GroupModel>

}

class GroupRepositoryImpl(private val queries: GroupQueries): GroupRepository {
    override suspend fun createGroup(name: String, creator: Int): GroupModel =
        queries.insert(name, creator).awaitAsOne().toModel()

    override suspend fun getGroups(): List<GroupModel> =
        queries

    override suspend fun getGroupsAsFlow(): Flow<List<GroupModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupsForUser(uid: UserId): List<GroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupsForUserAsFlow(uid: UserId): Flow<List<GroupModel>> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroup(groupId: GroupId): GroupModel {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupAsFlow(groupId: GroupId): Flow<GroupModel> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteGroup(groupId: GroupId): Boolean {
        TODO("Not yet implemented")
    }

}