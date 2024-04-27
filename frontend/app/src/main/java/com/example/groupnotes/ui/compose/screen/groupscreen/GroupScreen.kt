package com.example.groupnotes.ui.compose.screen.groupscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.groupnotes.api.Group
import com.example.groupnotes.ui.compose.CustomToolbar
import com.example.groupnotes.ui.compose.GridItem
import com.example.groupnotes.ui.compose.NumberIcon
import com.example.groupnotes.ui.compose.RoundedDropdownButton
import com.example.groupnotes.ui.compose.screen.NoteScreen

class GroupScreen() : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        val groups = listOf(
            Group(1, "Group 1", 0),
            Group(2, "Group 2", 0),
            Group(3, "Group 3", 0),
            Group(4, "Group 4", 0),
            Group(5, "Group 5", 0),
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CustomToolbar(
                title = "Groups",
                onBackClick = null
            )
            GroupsScreen(
                groups = groups,
                onGroupClick = { navigator.push(NoteScreen(it.id)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    val groups = listOf(
        Group(0, "asdf", 0),
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
            onGroupClick = {}
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
    onGroupClick: (Group) -> Unit
) {
    CustomGrid(
        items = groups,
        itemContent = { GroupCard(it, onClick = { onGroupClick(it) }) },
    )
}

@Preview
@Composable
fun GroupCardPreview() {
    GroupCard(group = Group(0, "Group", 0), onClick = {})
}

@Composable
fun GroupCard(
    group: Group,
    onClick: () -> Unit
) {
    GridItem(
        content = {
            Text(text = group.name)
        },
        onClick = onClick,
        contentTopStart = {
            NumberIcon(number = 2)
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}
