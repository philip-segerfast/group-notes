package org.robphi.groupnotes.ui.screen.group

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.ui.CustomGrid
import org.robphi.groupnotes.ui.CustomToolbar
import org.robphi.groupnotes.ui.GridItem
import org.robphi.groupnotes.ui.NumberIcon
import org.robphi.groupnotes.ui.RoundedDropdownButton
import org.robphi.groupnotes.util.createDummy
import kotlin.random.Random

@Composable
fun GroupScreenUI(
    group: Group?,
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onNoteLongClick: (Note) -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CustomToolbar(
            title = "Notes for group ${group?.name} (#${group?.id})",
            onBackClick = onBackPressed
        )

        Box(Modifier.fillMaxSize()) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                AnimatedVisibility(group == null) {
                    CircularProgressIndicator()
                }
            }

            if(group != null) {
                GroupScreenContent(
                    notes = notes,
                    onNoteClick = onNoteClick,
                    onNoteLongClick = onNoteLongClick
                )
            }
        }
    }
}

@Composable
fun GroupScreenContent(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onNoteLongClick: (Note) -> Unit,
) {
    CustomGrid(
        items = notes,
        itemContent = {
            NoteCard(
                note = it,
                onClick = { onNoteClick(it) },
                onLongClick = { onNoteLongClick(it) }
            )
        },
    )
}

@Composable
fun NoteCard(
    note: Note,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    GridItem(
        content = {
            Text(text = note.title)
        },
        onClick = onClick,
        onLongClick = onLongClick,
        contentTopStart = {
            NumberIcon(number = Random.nextInt(1, 7))
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GroupScreenPreview() {
    val notes = listOf(
        Note.createDummy("1"),
        Note.createDummy("2"),
        Note.createDummy("3"),
        Note.createDummy("4"),
        Note.createDummy("5"),
    )

    Box(Modifier.fillMaxSize()) {
        GroupScreenUI(
            group = Group.createDummy("Family Group"),
            notes = notes,
            onNoteClick = {},
            onNoteLongClick = {},
            onBackPressed = {},
        )
    }
}
