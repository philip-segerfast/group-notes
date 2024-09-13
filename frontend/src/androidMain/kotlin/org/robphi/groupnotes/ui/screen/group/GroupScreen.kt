package org.robphi.groupnotes.ui.screen.group

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import org.robphi.groupnotes.api.Note

data class GroupScreen(private val groupId: Long) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel: GroupScreenViewModel = koinInject { parametersOf(groupId) }

        val group by viewModel.group.collectAsState()
        val notes by viewModel.notes.collectAsState()

        var clickedNote by remember { mutableStateOf<Note?>(null) }

        GroupScreenUI(
            group = group,
            notes = notes,
            onNoteClick = { clickedNote = it },
            onNoteLongClick = { viewModel.deleteNote(it) },
            onBackPressed = { navigator?.pop() }
        )
    }
}

