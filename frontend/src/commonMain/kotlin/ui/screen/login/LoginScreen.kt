package ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import org.koin.compose.koinInject

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        val loginScreenViewModel: LoginScreenViewModel = koinInject()

        LoginScreenUI(
            onSignInButtonPressed = {
                loginScreenViewModel.onSignIn()
            },
        )
    }
}

@Composable
fun LoginScreenUI(
    modifier: Modifier = Modifier,
    onSignInButtonPressed: () -> Unit,
) {
    Column(
        modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Button(onClick = onSignInButtonPressed) {
            Text(text = "Sign in with Google")
        }
    }
}