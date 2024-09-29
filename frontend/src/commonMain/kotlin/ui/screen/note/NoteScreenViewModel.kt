package ui.screen.note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import api.note.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import model.Note
import model.NoteId
import org.koin.core.component.KoinComponent

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