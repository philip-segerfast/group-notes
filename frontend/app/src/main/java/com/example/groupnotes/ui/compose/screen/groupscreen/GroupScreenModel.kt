package com.example.groupnotes.ui.compose.screen.groupscreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.example.groupnotes.api.Group
import com.example.groupnotes.api.groupRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GroupScreenModel: ScreenModel {

    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups = _groups.asStateFlow()

    init {
        screenModelScope.launch {
            _groups.value = groupRepository.getAllGroups()
        }
    }

    override fun onDispose() {
        super.onDispose()
    }
}