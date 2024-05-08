package org.robphi.groupnotes.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import cafe.adriel.voyager.navigator.Navigator
import com.example.groupnotes.ui.compose.screen.groups.GroupScreen
import com.example.groupnotes.ui.compose.theme.GroupNotesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GroupNotesTheme {
                // A surface container using the 'background' color from the theme
                Navigator(screen = GroupScreen())
            }
        }
    }
}
