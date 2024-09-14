package org.robphi.groupnotes.ui.screen.group

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.robphi.groupnotes.api.Group
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.group.GroupRepository
import org.robphi.groupnotes.api.note.NoteRepository

class GroupScreenViewModel(
    private val groupId: Long,
    private val groupRepository: GroupRepository,
    private val noteRepository: NoteRepository
): ViewModel(), KoinComponent {

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    private val _group = MutableStateFlow<Group?>(null)
    val group = _group.asStateFlow()

    init {
        loadNotes()
        loadGroup()
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note.id)
    }

    fun createNote(title: String, content: String) {
        viewModelScope.launch {
            val note = noteRepository.createNote(title, content, groupId)
            println("Create note response: $note")
            loadNotes()
        }
    }

    private fun loadGroup() {
        viewModelScope.launch {
            println("Getting group with ID $groupId")
            _group.value = groupRepository.getGroupById(groupId).getOrThrow()
        }
    }

    fun loadNotes() {
        viewModelScope.launch {
            _notes.value = noteRepository.getNotes(groupId)
        }
    }

}