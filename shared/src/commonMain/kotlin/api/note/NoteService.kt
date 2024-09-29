package api.note

import model.CreateNoteRequest
import model.Note

interface NoteService {
    suspend fun createNote(note: CreateNoteRequest): Note

    suspend fun updateNote(note: Note): Note

    suspend fun deleteNote(noteId: Long)

    suspend fun getNoteById(id: Long): Note

    suspend fun getNotes(groupId: Long): List<Note>
}
