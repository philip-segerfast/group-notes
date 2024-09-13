package org.robphi.groupnotes.ui.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.NoteId
import org.robphi.groupnotes.api.note.NoteRepository

class NoteScreenViewModel(
    private val noteId: NoteId,
    private val noteRepository: NoteRepository,
): ViewModel(), KoinComponent {
    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch { fetchNote() }
        }
    }

    private suspend fun fetchNote() {
        _note.value = noteRepository.getNoteById(noteId)
    }
}