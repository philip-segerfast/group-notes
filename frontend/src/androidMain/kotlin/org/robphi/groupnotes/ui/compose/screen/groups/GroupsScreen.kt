package org.robphi.groupnotes.ui.compose.screen.groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.ui.compose.CustomToolbar
import org.robphi.groupnotes.ui.compose.GridItem
import org.robphi.groupnotes.ui.compose.NumberIcon
import org.robphi.groupnotes.ui.compose.RoundedDropdownButton
import org.robphi.groupnotes.ui.compose.screen.notes.NotesScreen

class GroupScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val model: GroupsScreenViewModel = koinInject()

        val groups by model.groups.collectAsState()
        val users by model.users.collectAsState()

        var showCreateGroupDialog by remember { mutableStateOf(false) }

        MaterialTheme {
            Scaffold(
                content = { padding ->
                    Column(
                        modifier = Modifier.padding(padding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CustomToolbar(
                            title = "Groups",
                            onBackClick = null,
                            onReload = { model.fetchGroupsAsync() }
                        )
                        GroupsScreen(
                            groups = groups,
                            onGroupClick = { navigator.push(NotesScreen(it.id)) },
                            onGroupLongClick = { model.deleteGroup(it.id) }
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(onClick = { showCreateGroupDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                }
            )

            if(showCreateGroupDialog) {
                Dialog(onDismissRequest = { showCreateGroupDialog = false }) {
                    CreateGroup(
                        users = users,
                        onDismiss = {showCreateGroupDialog = false},
                        onCreateGroup = { groupName, members -> model.createGroup(groupName, members.map { it.id }) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    val groups = listOf(
        Group(0, "ASsjadfdoiajisd foaisjdfopia jsddf poijasd f", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        GroupsScreen(
            groups = groups,
            onGroupClick = {},
            onGroupLongClick = {}
        )
    }
}

@Composable
fun <T: Any> CustomGrid(
    items: List<T>,
    itemContent: @Composable (T) -> Unit
) {
    val spacing = 24.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(spacing),
        horizontalArrangement = Arrangement.spacedBy(spacing),
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        items(items) { item ->
            itemContent(item)
        }
    }
}

@Composable
fun GroupsScreen(
    groups: List<Group>,
    onGroupClick: (Group) -> Unit,
    onGroupLongClick: (Group) -> Unit,
) {
    CustomGrid(
        items = groups,
        itemContent = {
            GroupCard(
                group = it,
                onClick = { onGroupClick(it) },
                onLongClick = { onGroupLongClick(it) }
            )
        },
    )
}

@Preview
@Composable
fun GroupCardPreview() {
    GroupCard(group = Group(0, "Group", 0), onClick = {}, onLongClick = {})
}

@Composable
fun GroupCard(
    group: Group,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    GridItem(
        content = {
            Text(text = group.name, textAlign = TextAlign.Center)
        },
        onClick = onClick,
        onLongClick = onLongClick,
        contentTopStart = {
            NumberIcon(number = 2)
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}
