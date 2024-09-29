package ui.screen.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import ui.screen.note.NoteScreen

data class GroupScreen(private val groupId: Long) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: GroupScreenViewModel = koinInject { parametersOf(groupId) }

        val group by viewModel.group.collectAsState()
        val notes by viewModel.notes.collectAsState()

        GroupScreenUI(
            group = group,
            notes = notes,
            onNoteClick = { navigator.push(NoteScreen(it.id)) },
            onNoteLongClick = { viewModel.deleteNote(it) },
            onBackPressed = { navigator.pop() },
            onReload = { viewModel.loadNotes() },
            onCreateNote = { title, content -> viewModel.createNote(title, content) }
        )
    }
}

