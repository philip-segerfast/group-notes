package org.robphi.groupnotes.ui.screen.grouplist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.ui.CustomGrid
import org.robphi.groupnotes.ui.CustomToolbar
import org.robphi.groupnotes.ui.GridItem
import org.robphi.groupnotes.ui.NumberIcon
import org.robphi.groupnotes.ui.RoundedDropdownButton

/**
 * Contents of entire group list screen
 * */
@Composable
fun GroupListScreenUI(
    groups: List<Group>,
    onCreateGroup: () -> Unit,
    onDeleteGroup: (Group) -> Unit,
    onFetchGroups: () -> Unit,
    onGroupClick: (Group) -> Unit,
) {
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
                        onReload = onFetchGroups
                    )
                    CustomGrid(
                        items = groups,
                        itemContent = {
                            GroupCard(
                                group = it,
                                onClick = { onGroupClick(it) },
                                onLongClick = { onDeleteGroup(it) }
                            )
                        },
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { onCreateGroup() }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }
        )
    }
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

@Preview(showBackground = true)
@Composable
fun GroupsScreenContentPreview() {
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
        GroupListScreenUI(
            groups = groups,
            onCreateGroup = {},
            onDeleteGroup = {},
            onFetchGroups = {},
            onGroupClick = {}
        )
    }
}
