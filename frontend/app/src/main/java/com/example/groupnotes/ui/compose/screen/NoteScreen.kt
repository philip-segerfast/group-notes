package com.example.groupnotes.ui.compose.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.example.groupnotes.ui.compose.CustomToolbar
import com.example.groupnotes.ui.compose.GridItem
import com.example.groupnotes.ui.compose.NumberIcon
import com.example.groupnotes.ui.compose.RoundedDropdownButton
import com.example.groupnotes.ui.compose.screen.groupscreen.CustomGrid
import kotlin.random.Random

data class NoteScreen(private val groupId: Long) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val notes = listOf(
            Note(1, "Note 1"),
            Note(2, "Note 2"),
            Note(3, "Note 3"),
            Note(4, "Note 4"),
            Note(5, "Note 5"),
        )

        var clickedNote by remember { mutableStateOf<Note?>(null) }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomToolbar(
                title = "Notes for group #$groupId",
                onBackClick = { navigator?.pop() }
            )

            Text("Clicked note: $clickedNote")

            NoteScreen(
                notes,
                onNoteClick = { clickedNote = it }
            )
        }

    }
}

data class Note(
    val noteId: Int,
    val title: String
)

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    val notes = listOf(
        Note(1, "Note 1"),
        Note(2, "Note 2"),
        Note(3, "Note 3"),
        Note(4, "Note 4"),
        Note(5, "Note 5"),
    )

    NoteScreen(notes, onNoteClick = {})
}

@Composable
fun NoteCard(note: Note, onClick: () -> Unit) {
    GridItem(
        content = {
            Text(text = note.title)
        },
        onClick = onClick,
        contentTopStart = {
            NumberIcon(number = Random.nextInt(1, 7))
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}

@Composable
fun NoteScreen(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit
) {
    CustomGrid(
        items = notes,
        itemContent = { NoteCard(it, onClick = { onNoteClick(it) }) },
    )
}