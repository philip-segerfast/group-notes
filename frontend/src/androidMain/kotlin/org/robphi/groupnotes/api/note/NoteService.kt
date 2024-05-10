package org.robphi.groupnotes.api.note

import org.robphi.groupnotes.model.Note
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteService {

    @POST("note/create/{noteId}/{title}")
    suspend fun createNote(@Path("noteId") noteId: Long, @Path("title") title: String): Note

    @PATCH("note/update")
    suspend fun updateNote(note: Note): String

    @DELETE
    suspend fun deleteNote(noteId: Long): String

    @GET("/note/{id}")
    suspend fun getNoteById(@Path("id") id: Long): Note = TODO()

    @GET("note/{groupId}")
    suspend fun getNotes(@Path("groupId") groupId: Long): List<Note> = TODO()

}