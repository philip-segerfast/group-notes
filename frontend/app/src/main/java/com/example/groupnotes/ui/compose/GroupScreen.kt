package com.example.groupnotes.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groupnotes.api.Group

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    val groups = listOf(
        Group(0, "asdf", 0),
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
        GroupsScreen(groups = groups)
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
fun GroupsScreen(groups: List<Group>) {
    CustomGrid(
        items = groups,
        itemContent = { GroupCard(it) }
    )
}

@Preview
@Composable
fun GroupCardPreview() {
    GroupCard(group = Group(0, "Group", 0))
}

@Composable
fun GroupCard(group: Group) {
    GridItem(
        content = {
            Text(text = group.name)
        },
        onClick = {
                  
        },
        contentTopStart = {
            NumberIcon(number = 2)
        },
        contentTopEnd = {
            RoundedDropdownButton()
        }
    )
}
