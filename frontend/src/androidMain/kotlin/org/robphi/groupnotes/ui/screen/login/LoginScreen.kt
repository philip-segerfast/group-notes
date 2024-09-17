package org.robphi.groupnotes.ui.screen.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import org.robphi.groupnotes.shared.BuildKonfig

class LoginScreen : Screen {
    @Composable
    override fun Content() {
        LoginScreenUI()
    }
}

@Composable
fun LoginScreenUI(modifier: Modifier = Modifier) {
    GoogleSignInButton {

    }
}