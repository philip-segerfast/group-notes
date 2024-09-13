package org.robphi.groupnotes.api.note

import kotlinx.collections.immutable.persistentMapOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.NoteList
import org.robphi.groupnotes.util.createDummy

class FakeNoteService : NoteService {
    private var notes = MutableStateFlow(persistentMapOf<Long, Note>())

    override suspend fun createNote(noteId: Long, title: String): Note {
        return Note.createDummy("adsf")
    }

    override suspend fun updateNote(note: Note): String {
        notes.update { it.put(note.id, note) }
        return "Note ${note.id} updated."
    }

    override suspend fun deleteNote(noteId: Long): String {
        notes.update { it.remove(noteId) }
        return "Note #$noteId removed."
    }

    override suspend fun getNoteById(id: Long): Note {
        return notes.value[id]!!
    }

    override suspend fun getNotes(groupId: Long): NoteList {
        return NoteList(notes.value.values.toList())
    }
}