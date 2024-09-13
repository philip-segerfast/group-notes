package org.robphi.groupnotes.ui.screen.grouplist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.robphi.groupnotes.api.User
import org.robphi.groupnotes.ui.CustomTextField2
import org.robphi.groupnotes.ui.SearchArea

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateGroupDialogContent(
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

    CreateGroupDialogContent(
        items,
        onCreateGroup = { _, _ -> },
        onDismiss = {}
    )
}