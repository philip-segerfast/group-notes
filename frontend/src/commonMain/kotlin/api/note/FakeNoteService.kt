package api.note

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import model.CreateNoteRequest
import model.Note
import util.createDummy

class FakeNoteService : NoteService {

    private var notes = MutableStateFlow(persistentMapOf<Long, Note>())

    override suspend fun createNote(note: CreateNoteRequest): Note {
        return Note.createDummy("adsf")
    }

    override suspend fun updateNote(note: Note): Note {
        notes.update { it.put(note.id, note) }
        return note
    }

    override suspend fun deleteNote(noteId: Long) {
        notes.update { it.remove(noteId) }
        println("Note #$noteId removed.")
    }

    override suspend fun getNoteById(id: Long): Note {
        return notes.value[id]!!
    }

    override suspend fun getNotes(groupId: Long): List<Note> {
        return notes.value.values.toList()
    }

}