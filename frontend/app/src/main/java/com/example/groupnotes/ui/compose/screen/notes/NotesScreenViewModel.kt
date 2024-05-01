package com.example.groupnotes.ui.compose.screen.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cafe.adriel.voyager.hilt.ScreenModelFactory
import com.example.groupnotes.api.note.NoteRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
//            noteRepository.getNotes().collect { notes ->
//                _notes.value = notes
//            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(groupId: Long): NotesScreenViewModel
    }

}