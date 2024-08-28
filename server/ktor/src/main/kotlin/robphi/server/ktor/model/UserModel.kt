package robphi.server.ktor.model

import kotlinx.serialization.Serializable

typealias UserEntity = robphi.server.ktor.database.User

typealias UserId = Int

@Serializable
data class UserModel(
    val id: UserId,
    val name: String,
)

fun UserEntity.toModel(): UserModel = UserModel(id, name)
