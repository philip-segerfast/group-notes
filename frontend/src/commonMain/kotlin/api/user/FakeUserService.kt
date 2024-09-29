package api.user

import kotlinx.coroutines.GlobalScope
import model.GetUserResponse
import model.StoredUserResponse
import model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import auth.Auth
import kotlin.time.Duration.Companion.seconds

class FakeUserService(private val auth: Auth) : UserService {

    private var userIdCounter = 0L

    private val _users = MutableStateFlow(emptyList<User>())

    init {
        @Suppress("OPT_IN_USAGE")
        GlobalScope.launch {
            initFakeUsers()
        }
    }

    private suspend fun initFakeUsers() {
        println("Creating fake users...")
        val clientUserId = auth.getUserId()
        println("Client userId: $clientUserId")
        val userNames = listOf(
            "Kevin",
            "Albin",
            "Hanna",
            "Gustav",
            "Anna",
            "Pelle",
            "Nils",
            "Jakob",
            "Emma",
        )
        val users = userNames.mapIndexed { index, name ->
            val indexLong = index.toLong()
            // If there's a clash with clientUserId, make it unique
            val userId = indexLong.takeIf { it != clientUserId } ?: (indexLong + userNames.count())
            User(userId, name).also { userIdCounter++ }
        }
        val usersWithClient = users + User(clientUserId, "Client")
        _users.value = usersWithClient
    }

    override suspend fun getUserById(id: Long): Result<GetUserResponse> = runCatching {
        val user = withTimeout(3.seconds) {
            _users
                .map { user ->
                    user.find { it.id == id }
                }
                .first()
        }
        GetUserResponse(user)
    }

    override suspend fun getAllUsers(): Result<StoredUserResponse> = runCatching {
        val users = withTimeout(3.seconds) {
            _users.first { it.isNotEmpty() }
        }
        StoredUserResponse(users)
    }

    override suspend fun createUser(name: String): Result<Unit> {
        _users.update { it + User(userIdCounter++, name) }
        return Result.success(Unit)
    }

    override suspend fun updateUser(): Result<Unit> {
        TODO("Undefined behaviour")
    }
}