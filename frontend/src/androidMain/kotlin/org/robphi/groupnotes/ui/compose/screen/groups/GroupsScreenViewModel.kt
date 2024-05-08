package com.example.groupnotes.ui.compose.screen.groups

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groupnotes.api.Group
import com.example.groupnotes.api.User
import com.example.groupnotes.api.group.GroupRepository
import com.example.groupnotes.api.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsScreenViewModel @Inject constructor(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
): ViewModel() {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    fun createGroup(name: String, members: List<User>): Job = viewModelScope.launch {
        groupRepository.createGroup(name)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                _groups.value = groupRepository.getAllGroups().getOrNull()?.userGroups ?: emptyList()
            }
            launch {
                _users.value = userRepository.getAllUsers().getOrNull()?.users ?: emptyList()
            }
        }
    }
}