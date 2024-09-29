package api.note

import model.CreateNoteRequest
import model.Note
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitNoteService : NoteService {

    @POST("note/create")
    override suspend fun createNote(@Body note: CreateNoteRequest): Note

    @PATCH("note/update")
    override suspend fun updateNote(@Body note: Note): Note

    @DELETE("note/delete/{noteId}")
    override suspend fun deleteNote(@Path("noteId") noteId: Long)

    @GET("/note/{id}")
    override suspend fun getNoteById(@Path("id") id: Long): Note

    @GET("note/get_notes/{groupId}")
    override suspend fun getNotes(@Path("groupId") groupId: Long): List<Note>

}