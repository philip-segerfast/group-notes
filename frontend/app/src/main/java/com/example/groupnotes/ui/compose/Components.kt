package com.example.groupnotes.ui.compose

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.TextFieldDecorator
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groupnotes.api.User
import com.example.groupnotes.ui.compose.theme.LightBlue

@Preview(showBackground = true)
@Composable
fun GridItemPreview() {
    Box(
        Modifier
            .size(250.dp)
            .padding(16.dp)
    ) {
        GridItem(
            content = {
                Text("Hello")
            },
            onClick = {},
            contentTopEnd = {
                RoundedDropdownButton()
            },
            contentTopStart = {
                NumberIcon(3)
            }
        )
    }
}

@Composable
fun CustomToolbar(
    title: String,
    onBackClick: (() -> Unit)? = null
) {
    Row(
        Modifier
            .height(64.dp)
            .fillMaxWidth()
            .background(Color.Blue.copy(0.1f))
            .padding(16.dp)
        ,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(onBackClick != null) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        }

        ProvideTextStyle(value = MaterialTheme.typography.titleMedium) {
            Text(text = title)
        }
    }
}

@Composable
fun RoundedDropdownButton() {
    Box(
        modifier = Modifier
            .shadow(4.dp, CircleShape, clip = false)
            .background(LightBlue, RoundedCornerShape(100))
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Default.Menu, contentDescription = null, tint = Color.White)
        }
    }
}

@Preview
@Composable
fun NumberIconPreview() {
    NumberIcon(number = 3)
}

@Composable
fun NumberIcon(number: Int) {
    Box(
        modifier = Modifier
            .scale(0.8f)
            .shadow(4.dp, CircleShape, clip = false)
            .background(Color.White, RoundedCornerShape(100))
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Text("$number")
        }
    }
}

@Composable
fun GridItem(
    content: @Composable BoxScope.() -> Unit,
    onClick: () -> Unit,
    contentTopEnd: @Composable () -> Unit,
    contentTopStart: @Composable () -> Unit
) {
    val cornerShape = RoundedCornerShape(8.dp)
    val iconSize = 24.dp
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
        ,
        contentAlignment = Alignment.Center,
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(iconSize / 3)
                .shadow(elevation = 8.dp, cornerShape, clip = false)
                .background(Color.White, cornerShape)
                .clickable(onClick = onClick)
            ,
            contentAlignment = Alignment.Center,
            content = { content() }
        )

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(iconSize),
            contentAlignment = Alignment.Center
        ) {
            contentTopStart()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(iconSize),
            contentAlignment = Alignment.Center
        ) {
            contentTopEnd()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateGroupPreview() {
    val items = listOf(
        User(0, "Benny"),
        User(0, "Link"),
        User(0, "Anders"),
        User(0, "Johnny"),
        User(0, "Kent"),
        User(0, "Alex"),
        User(0, "Juan"),
        User(0, "Robin"),
        User(0, "Chris"),
    )

    CreateGroup(
        items,
        onCreateGroup = { _, _ -> },
        onDismiss = {}
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateGroup(
    users: List<User>,
    onCreateGroup: (name: String, members: List<User>) -> Unit,
    onDismiss: () -> Unit
) {
    val selectedUsers = remember { mutableStateListOf<User>() }
    val unselectedUsers by remember(users, selectedUsers) {
        derivedStateOf { users.filter { it !in selectedUsers } }
    }
    val groupName = rememberTextFieldState()

    val shape = RoundedCornerShape(8.dp)

    Column(
        Modifier
            .padding(32.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
            .clip(shape)
    ) {
        Row {
            Text("Group name: ")
            CustomTextField2(state = groupName)
        }
        Spacer(Modifier.height(8.dp))
        SearchArea(
            items = unselectedUsers,
            searchMatcher = { search, user -> user.name.contains(search, ignoreCase = true) },
            itemTextProvider = { it.name },
            onItemClick = { selectedUsers.add(it) }
        )

        Spacer(Modifier.height(16.dp))

        AnimatedVisibility(visible = selectedUsers.isNotEmpty()) {
            Column(
                Modifier
                    .heightIn(max = 250.dp)
                    .fillMaxWidth()
                    .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if(selectedUsers.isNotEmpty()) {
                    Text("Selected users:")
                }
                selectedUsers.forEach { user ->
                    Column {
                        Text(
                            user.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedUsers -= user }
                                .padding(8.dp)
                        )
                        HorizontalDivider()
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.End) {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
            Spacer(Modifier.width(8.dp))
            TextButton(onClick = { onCreateGroup(groupName.text.toString(), selectedUsers) }) {
                Text("Create")
            }
        }
    }
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomTextField2(state: TextFieldState) {
    BasicTextField2(
        state = state,
        decorator = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color.Blue.copy(0.1f), RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                innerTextField()
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T: Any> SearchComponent(
    items: List<T>,
    /** If the item matches the search */
    searchMatcher: (String, T) -> Boolean,
    itemTextProvider: (T) -> String,
    onItemClick: (T) -> Unit
) {
    val searchText = rememberTextFieldState()
    val matchedItems by remember(items) {
        derivedStateOf {
            when {
                searchText.text.isBlank() -> items
                else -> {
                    items.filter { item ->
                        searchMatcher(searchText.text.toString(), item)
                    }
                }
            }
        }
    }

    Column {
        CustomTextField2(state = searchText)
        LazyColumn(
            Modifier
                .heightIn(max = 200.dp)
        ) {
            items(matchedItems) { item ->
                Column(
                    Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(item) }
                        .padding(8.dp)
                ) {
                    Text(text = itemTextProvider(item))
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun <T: Any> SearchArea(
    items: List<T>,
    searchMatcher: (String, T) -> Boolean,
    itemTextProvider: (T) -> String,
    onItemClick: (T) -> Unit
) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, Color.Blue, RoundedCornerShape(8.dp))
    ) {
        SearchComponent(
            items = items,
            searchMatcher = searchMatcher,
            itemTextProvider = itemTextProvider,
            onItemClick = onItemClick
        )
    }
}











