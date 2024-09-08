package org.robphi.groupnotes.ui.compose.screen.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.logger.Logger
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.GroupId
import org.robphi.groupnotes.api.User
import org.robphi.groupnotes.api.UserId
import org.robphi.groupnotes.api.group.GroupRepository
import org.robphi.groupnotes.api.user.UserRepository

class GroupsScreenViewModel(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
): ViewModel() {
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    fun createGroup(name: String, members: List<UserId>): Job = viewModelScope.launch {
        groupRepository.createGroup(name, members)
    }

    fun deleteGroup(groupId: GroupId) = viewModelScope.launch {
        groupRepository.deleteGroup(groupId)
        fetchGroups()
    }

    fun fetchGroupsAsync() = viewModelScope.launch {
        fetchGroups()
    }

    private suspend fun fetchGroups() {
        _groups.value = groupRepository.getAllUserGroups().getOrNull() ?: emptyList()
        println("Groups: ${_groups.value}")
    }

    private suspend fun fetchUsers() {
        _users.value = userRepository.getAllUsers().getOrNull()?.users ?: emptyList()
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                fetchGroups()
            }
            launch {
                fetchUsers()
            }
        }
    }
}