package org.robphi.groupnotes.ui.screen.group

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.robphi.groupnotes.api.CreateNoteRequest
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.ui.CustomGrid
import org.robphi.groupnotes.ui.CustomToolbar
import org.robphi.groupnotes.ui.GridItem
import org.robphi.groupnotes.ui.NumberIcon
import org.robphi.groupnotes.ui.RoundedDropdownButton
import org.robphi.groupnotes.util.createDummy
import kotlin.random.Random

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GroupScreenUI(
    group: Group?,
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    onNoteLongClick: (Note) -> Unit,
    onCreateNote: (title: String, content: String) -> Unit,
    onBackPressed: () -> Unit,
    onReload: () -> Unit,
) {
    var showGroupSettings by remember { mutableStateOf(false) }
    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState)
    val scope = rememberCoroutineScope()

//    LaunchedEffect(bottomSheetState.isCollapsed) {
//        if(bottomSheetState.isCollapsed) {
//            showGroupSettings = false
//        }
//    }

    

    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            if(bottomSheetState.isExpanded) {
                Column {
                    Button(onClick = {}) {
                        Text("Leave group")
                    }
                    Column {
                        Text("Anna")
                        Text("David")
                        Text("Kenny")
                        Text("Robin")
                        Text("Kalle")
                        Text("Philip")
                        Text("Jasmin")
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onCreateNote("Title", "Content") }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomToolbar(
                title = "Notes for group ${group?.name} (#${group?.id})",
                onBackClick = onBackPressed,
                onReload = onReload,
                additionalContent = {
                    GroupSettingsIcon(
                        onClick = {
                            scope.launch {
                                showGroupSettings = true
                            }
                        }
                    )
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
    }
}

@Composable
fun GroupSettingsIcon(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(Icons.Default.Settings, contentDescription = "Manage group")
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
            onReload = {},
            onCreateNote = { _, _ -> }
        )
    }
}
