package com.example.groupnotes.ui.compose.screen.groupscreen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch

class GroupScreenModel: ScreenModel {



    override fun onDispose() {
        super.onDispose()

        screenModelScope.launch {

        }
    }
}