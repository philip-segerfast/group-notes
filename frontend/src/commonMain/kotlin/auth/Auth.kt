package auth

import kotlinx.coroutines.flow.StateFlow
import model.UserId

interface Auth {

    val userId: StateFlow<UserId?>
    /** Get userId, blocking until it's available. */
    fun getUserIdBlocking(): UserId
    suspend fun getUserId(): UserId

    fun authenticate(idToken: String, accessToken: String): Result<AuthenticationResponse>

    data class AuthenticationResponse(
        val authToken: String
    )
}