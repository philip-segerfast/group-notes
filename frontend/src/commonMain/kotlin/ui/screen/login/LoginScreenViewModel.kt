package ui.screen.login

import androidx.lifecycle.ViewModel
import auth.Auth
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import kotlin.time.Duration.Companion.seconds

class LoginScreenViewModel(
    private val auth: Auth,
) : ViewModel(), KoinComponent {
    private val _authState = MutableStateFlow<AuthState?>(null)
    val authenticationStatus = _authState.asStateFlow()

    fun onSignInResult() {

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun onSignIn() {
        GlobalScope.launch {
            delay(2.seconds)
            _authState.value = AuthState.Authenticating.SigningInWithGoogle
        }
    }

    sealed interface AuthState {
        sealed interface Authenticating : AuthState {
            data object SigningInWithGoogle : Authenticating
            data object AuthenticatingWithBackend : Authenticating
        }
        data class Error(val errorMessage: String) : AuthState
        data class Success(val authToken: String) : AuthState
    }

}
