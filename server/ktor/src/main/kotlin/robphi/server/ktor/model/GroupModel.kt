package robphi.server.ktor.model

typealias GroupId = Int

typealias GroupEntity = robphi.server.ktor.database.Group

data class GroupModel(
    val id: GroupId,
    val name: String,
    val creator: UserId
)

fun GroupEntity.toModel() = GroupModel(
    id = id,
    name = name,
    creator = userId
)