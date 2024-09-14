package org.robphi.groupnotes.ui.screen.grouplist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.koinInject
import org.robphi.groupnotes.ui.screen.group.GroupScreen

class GroupListScreen : Screen {

    @Composable
    override fun Content() {
        val model: GroupListScreenViewModel = koinInject()
        val navigator = LocalNavigator.currentOrThrow
        val groups by model.groups.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        var showCreateGroupDialog by remember { mutableStateOf(false) }

        GroupListScreenUI(
            groups = groups,
            onCreateGroup = { showCreateGroupDialog = true },
            onDeleteGroup = { model.deleteGroup(it.id) },
            onFetchGroups = { model.fetchGroupsAsync() },
            onGroupClick = { navigator.push(GroupScreen(it.id)) }
        )

        if(showCreateGroupDialog) {
            val users by model.users.collectAsState()

            Dialog(onDismissRequest = { showCreateGroupDialog = false }) {
                CreateGroupDialogContent(
                    users = users,
                    onDismiss = {showCreateGroupDialog = false},
                    onCreateGroup = { groupName, members -> model.createGroup(groupName, members.map { it.id }) }
                )
            }
        }
    }
}
