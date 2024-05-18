package org.robphi.groupnotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import org.koin.compose.KoinContext
import org.koin.core.context.KoinContext
import org.robphi.groupnotes.ui.compose.screen.groups.GroupScreen
import org.robphi.groupnotes.ui.compose.theme.GroupNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KoinContext {
                GroupNotesTheme {
                    // A surface container using the 'background' color from the theme
                    Navigator(screen = GroupScreen())
                }
            }

        }
    }
}
