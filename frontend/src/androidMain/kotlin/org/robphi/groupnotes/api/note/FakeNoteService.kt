package org.robphi.groupnotes.api.note

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.robphi.groupnotes.model.Note

class FakeNoteService : NoteService {
    private var notes = MutableStateFlow(persistentMapOf<Long, Note>())

    override suspend fun createNote(noteId: Long, title: String): Note = Note(noteId, title).also { note ->
        notes.update { it.put(noteId, note) }
    }

    override suspend fun updateNote(note: Note): String {
        notes.update { it.put(note.noteId, note) }
        return "Note ${note.noteId} updated."
    }

    override suspend fun deleteNote(noteId: Long): String {
        notes.update { it.remove(noteId) }
        return "Note #$noteId removed."
    }

    override suspend fun getNoteById(id: Long): Note {
        return notes.value[id]!!
    }

    override suspend fun getNotes(groupId: Long): List<Note> {
        return notes.value.values.toList()
    }
}