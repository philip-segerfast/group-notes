package org.robphi.groupnotes.ui.compose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.robphi.groupnotes.api.Note
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    val notes = listOf(
        Note.createDummy("Note 1"),
        Note.createDummy("Note 2"),
        Note.createDummy("Note 3"),
        Note.createDummy("Note 4"),
        Note.createDummy("Note 5"),
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
fun NoteScreen(notes: List<Note>) {
    CustomGrid(
        items = notes,
        itemContent = { NoteCard(it) }
    )
}