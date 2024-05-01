package com.example.groupnotes.ui.compose.screen.groupscreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.groupnotes.api.Group
import com.example.groupnotes.api.User
import com.example.groupnotes.deviceUserId
import com.example.groupnotes.groupService
import com.example.groupnotes.userService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupScreenModel: ScreenModel {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()
    
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    suspend fun createGroup(name: String, members: List<User>) {
        groupService.createGroup(name, deviceUserId.value!!)
    }

    init {
        screenModelScope.launch(Dispatchers.IO) {
            launch {
                _groups.value = groupService.getAllGroups().userGroups
            }
            launch {
                _users.value = userService.getAllUsers().users
            }
        }
    }

    override fun onDispose() {
        super.onDispose()
    }
}