package org.robphi.groupnotes.ui.screen.note

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import org.robphi.groupnotes.api.NoteId

data class NoteScreen(private val noteId: NoteId) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: NoteScreenViewModel = koinInject { parametersOf(noteId) }

        NoteScreenUI()
    }
}

@Composable
fun NoteScreenUI() {

}