package org.robphi.groupnotes.api.note

import org.robphi.groupnotes.api.CreateNoteRequest
import org.robphi.groupnotes.api.Note
import org.robphi.groupnotes.api.WrappedNote
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface NoteService {

    @POST("note/create")
    suspend fun createNote(
        @Body note: CreateNoteRequest
    ): Note

    @PATCH("note/update")
    suspend fun updateNote(@Body note: Note): Note

    @DELETE("note/delete/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: Long)

    @GET("/note/{id}")
    suspend fun getNoteById(@Path("id") id: Long): Note

    @GET("note/get_notes/{groupId}")
    suspend fun getNotes(@Path("groupId") groupId: Long): List<Note>

}