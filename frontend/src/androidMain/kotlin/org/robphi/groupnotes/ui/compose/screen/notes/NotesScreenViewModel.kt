package org.robphi.groupnotes.ui.compose.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.java.KoinJavaComponent.inject
import org.koin.java.KoinJavaComponent.injectOrNull
import org.robphi.groupnotes.api.note.NoteRepository
import org.robphi.groupnotes.model.Note

class NotesScreenViewModel(
    private val groupId: Long,
    private val noteRepository: NoteRepository
): ViewModel(), KoinComponent {

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

}