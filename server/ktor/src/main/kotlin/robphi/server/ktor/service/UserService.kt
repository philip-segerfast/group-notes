package robphi.server.ktor.service

import robphi.server.ktor.model.UserModel
import robphi.server.ktor.repo.UserRepository

interface UserService {
    suspend fun createUser(name: String): UserModel

    suspend fun updateUser(user: UserModel): Boolean

    suspend fun getUsers(): List<UserModel>

    suspend fun getUser(uid: Int): UserModel
}

class UserServiceImpl(private val userRepository: UserRepository): UserService {

    override suspend fun createUser(name: String) = userRepository.createUser(name)

    override suspend fun updateUser(user: UserModel) = userRepository.updateUser(user)

    override suspend fun getUsers(): List<UserModel> = userRepository.getUsers()

    override suspend fun getUser(uid: Int) = userRepository.getUser(uid)

}