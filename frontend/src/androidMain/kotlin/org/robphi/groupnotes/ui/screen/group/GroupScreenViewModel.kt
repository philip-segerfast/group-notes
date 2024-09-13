package org.robphi.groupnotes.ui.screen.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.note.NoteRepository

class GroupScreenViewModel(
    private val groupId: Long,
    private val noteRepository: NoteRepository
): ViewModel(), KoinComponent {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    init {
        loadNotes()
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note.id)
    }

    private fun loadNotes() {
        viewModelScope.launch {
            _notes.value = noteRepository.getNotes(groupId).notes
        }
    }

}