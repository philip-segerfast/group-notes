package ui.screen.grouplist

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
import ui.screen.group.GroupScreen

class GroupListScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel: GroupListScreenViewModel = koinInject()
        val groups by viewModel.groups.collectAsState()
        val navigator = LocalNavigator.currentOrThrow

        var showCreateGroupDialog by remember { mutableStateOf(false) }

        GroupListScreenUI(
            groups = groups,
            onCreateGroup = { showCreateGroupDialog = true },
            onDeleteGroup = { viewModel.deleteGroup(it.id) },
            onFetchGroups = { viewModel.fetchGroupsAsync() },
            onGroupClick = { navigator.push(GroupScreen(it.id)) }
        )

        if(showCreateGroupDialog) {
            val users by viewModel.users.collectAsState()

            Dialog(onDismissRequest = { showCreateGroupDialog = false }) {
                CreateGroupDialogContent(
                    users = users,
                    onDismiss = {showCreateGroupDialog = false},
                    onCreateGroup = { groupName, members -> viewModel.createGroup(groupName, members.map { it.id }) }
                )
            }
        }
    }
}
