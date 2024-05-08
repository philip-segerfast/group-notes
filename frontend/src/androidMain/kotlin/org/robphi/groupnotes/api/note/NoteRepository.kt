package com.example.groupnotes.api.note

import com.example.groupnotes.auth.Auth
import com.example.groupnotes.model.Note

class NoteRepository(
    private val groupService: NoteService,
    private val auth: Auth
) {

    suspend fun createNote(noteId: Long, title: String) = groupService.createNote(noteId, title)

    suspend fun updateNote(note: Note) = groupService.updateNote(note)

    suspend fun deleteNote(noteId: Long) = groupService.deleteNote(noteId)

    suspend fun getNoteById(noteId: Long) = groupService.getNoteById(noteId)

    suspend fun getNotes(groupId: Long) = groupService.getNotes(groupId)

}