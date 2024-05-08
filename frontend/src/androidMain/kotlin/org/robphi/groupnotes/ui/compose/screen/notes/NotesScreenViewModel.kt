package com.example.groupnotes.ui.compose.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.groupnotes.api.note.NoteRepository
import com.example.groupnotes.model.Note
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = NotesScreenViewModel.Factory::class)
class NotesScreenViewModel @AssistedInject constructor(
    @Assisted private val groupId: Long,
    private val noteRepository: NoteRepository
): ViewModel() {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notes.value = noteRepository.getNotes(groupId)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(groupId: Long): NotesScreenViewModel
    }

}