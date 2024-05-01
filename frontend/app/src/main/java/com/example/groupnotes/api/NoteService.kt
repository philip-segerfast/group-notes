package com.example.groupnotes.api

import com.example.groupnotes.ui.compose.screen.groupscreen.Note
import retrofit2.http.GET
import retrofit2.http.Path

interface NoteService {

    @GET("/notes/{id}")
    suspend fun getNoteById(@Path("id") id: Long): Note

    @GET("notes/{groupId}")
    suspend fun getNotes(@Path("groupId") groupId: Long): List<Note>

}