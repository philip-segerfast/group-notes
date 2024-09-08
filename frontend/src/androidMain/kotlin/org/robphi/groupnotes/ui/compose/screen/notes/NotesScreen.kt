package org.robphi.groupnotes.ui.compose.screen.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.note.NoteRepository
import org.robphi.groupnotes.ui.compose.CustomToolbar
import org.robphi.groupnotes.ui.compose.GridItem
import org.robphi.groupnotes.ui.compose.NumberIcon
import org.robphi.groupnotes.ui.compose.RoundedDropdownButton
import org.robphi.groupnotes.ui.compose.screen.groups.CustomGrid
import kotlin.random.Random

data class NotesScreen(private val groupId: Long) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val viewModel: NotesScreenViewModel = koinInject<NotesScreenViewModel> { parametersOf(groupId) }

        val notes by viewModel.notes.collectAsState()

        var clickedNote by remember { mutableStateOf<Note?>(null) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomToolbar(
                title = "Notes for group #$groupId",
                onBackClick = { navigator?.pop() }
            )

            Text("Clicked note: $clickedNote")

            NoteListScreen(
                notes,
                onNoteClick = { clickedNote = it }
            )
        }

    }
}

class NotesScreenModel(
    private val noteId: Long,
    private val noteRepository: NoteRepository
) : ScreenModel {

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    init {
        screenModelScope.launch {
            fetchNote()
        }
    }

    private suspend fun fetchNote() {
        _note.value = noteRepository.getNoteById(noteId)
    }

    override fun onDispose() {
        super.onDispose()


    }
}

@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    val notes = listOf(
        Note.createDummy("1"),
        Note.createDummy("2"),
        Note.createDummy("3"),
        Note.createDummy("4"),
        Note.createDummy("5"),
    )

    NoteListScreen(notes, onNoteClick = {})
}

@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    GridItem(
        content = {
            Text(text = note.title)
        },
        onClick = onClick,
        onLongClick = {},
        contentTopStart = {
            NumberIcon(number = Random.nextInt(1, 7))
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}

@Composable
fun NoteListScreen(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit
) {
    CustomGrid(
        items = notes,
        itemContent = { NoteCard(it, onClick = { onNoteClick(it) }) },
    )
}