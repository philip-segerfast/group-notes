package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import di.koinStarted
import org.koin.compose.KoinContext
import ui.screen.login.LoginScreen
import ui.theme.GroupNotesTheme

@Composable
fun App() {
    val koinStarted by koinStarted.collectAsState()

    GroupNotesTheme {
        if(koinStarted) {
            KoinContext {
                // A surface container using the 'background' color from the theme
                Text("Hello World!")
                Navigator(screen = LoginScreen())
            }
        }
        else {
            // Loading screen
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Text("Loading...")
                    CircularProgressIndicator()
                }
            }
        }
    }
}