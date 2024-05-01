package com.example.groupnotes.api.note

import com.example.groupnotes.auth.Auth

class NoteRepository(
    private val groupService: NoteService,
    private val auth: Auth
) {

    suspend fun getNotes(groupId: Long) = groupService.getNotes(groupId)

    suspend fun getNoteById(noteId: Long) = groupService.getNoteById(noteId)

}