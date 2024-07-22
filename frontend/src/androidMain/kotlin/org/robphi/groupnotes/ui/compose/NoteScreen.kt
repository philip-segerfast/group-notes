package org.robphi.groupnotes.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.robphi.groupnotes.model.Note
import kotlin.random.Random

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

    NoteScreen(notes)
}

@Composable
fun NoteCard(note: Note) {
    GridItem(
        content = {
            Text(text = note.title)
        },
        onClick = {},
        contentTopStart = {
            NumberIcon(number = Random.nextInt(1, 7))
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}

@Composable
fun NoteScreen(notes: List<Note>) {
    CustomGrid(
        items = notes,
        itemContent = { NoteCard(it) }
    )
}