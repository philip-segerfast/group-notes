package org.robphi.groupnotes.api.note

import org.koin.core.component.KoinComponent
import org.robphi.groupnotes.api.CreateNoteRequest
import org.robphi.groupnotes.api.GroupId
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.auth.Auth

class NoteRepository(
    private val groupService: NoteService,
    private val auth: Auth
): KoinComponent {

    suspend fun createNote(
        title: String,
        content: String,
        groupId: GroupId
    ): Note = groupService.createNote(CreateNoteRequest(title, content, groupId))

    suspend fun updateNote(note: Note): Note = groupService.updateNote(note)

    suspend fun deleteNote(noteId: Long) = groupService.deleteNote(noteId)

    suspend fun getNoteById(noteId: Long) = groupService.getNoteById(noteId)

    suspend fun getNotes(groupId: Long) = groupService.getNotes(groupId)

}