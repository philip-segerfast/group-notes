package ui.screen.grouplist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.group.GroupRepository
import api.user.UserRepository
import auth.Auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Group
import model.GroupId
import model.User
import model.UserId
import org.koin.core.component.KoinComponent

class GroupListScreenViewModel(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository,
    private val auth: Auth
): ViewModel(), KoinComponent {
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
        _groups.value = groupRepository.getAllUserGroups().getOrThrow()
        println("Groups: ${_groups.value}")
    }

    private suspend fun fetchUsers() {
        _users.value = userRepository.getAllUsers().getOrThrow()
    }

    private suspend fun fetchAll() = coroutineScope {
        launch { fetchGroups() }
        launch { fetchUsers() }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            auth.userId.collect { userId ->
                if(userId != null) fetchAll()
            }
        }
    }
}