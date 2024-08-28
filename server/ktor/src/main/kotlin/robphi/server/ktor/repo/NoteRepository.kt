package robphi.server.ktor.repo

import app.cash.sqldelight.Query
import app.cash.sqldelight.async.coroutines.awaitAsList
import app.cash.sqldelight.async.coroutines.awaitAsOne
import app.cash.sqldelight.coroutines.asFlow
import co.touchlab.kermit.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import robphi.server.ktor.database.Note
import robphi.server.ktor.database.NoteQueries
import robphi.server.ktor.model.GroupId
import robphi.server.ktor.model.NoteId
import robphi.server.ktor.model.NoteModel
import robphi.server.ktor.model.UserId
import robphi.server.ktor.model.toModel

interface NoteRepository {

    suspend fun getNotes(): List<NoteModel>

    suspend fun getNotesAsFlow(): Flow<List<NoteModel>>

    suspend fun getNote(id: NoteId): NoteModel

    suspend fun getNoteAsFlow(id: NoteId): Flow<NoteModel>

    suspend fun createNote(title: String, content: String, groupId: GroupId, creator: UserId): NoteModel

    suspend fun deleteNote(id: NoteId): Boolean

    suspend fun getAllNotesForUser(id: UserId): List<NoteModel>

    suspend fun getAllNotesForUserAsFlow(id: UserId): Flow<List<NoteModel>>

    suspend fun getAllNotesForGroup(id: GroupId): List<NoteModel>

    suspend fun getAllNotesForGroupAsFlow(id: GroupId): Flow<List<NoteModel>>

}

class NoteRepositoryImpl(private val noteQueries: NoteQueries): NoteRepository {

    override suspend fun getNotes(): List<NoteModel> =
        noteQueries.selectAll().transformAsList(Note::toModel)

    override suspend fun getNotesAsFlow(): Flow<List<NoteModel>> =
        noteQueries.selectAll().transformAsListFlow(Note::toModel)

    override suspend fun getNote(id: NoteId): NoteModel =
        noteQueries.selectByNoteId(id).transformAsSingle(Note::toModel)

    override suspend fun getNoteAsFlow(id: NoteId): Flow<NoteModel> =
        noteQueries.selectByNoteId(id).transformAsOneFlow(Note::toModel)

    override suspend fun createNote(title: String, content: String, groupId: GroupId, creator: UserId): NoteModel =
        noteQueries.insertNote(title, content, groupId, creator).awaitAsOne().toModel()

    override suspend fun deleteNote(id: NoteId): Boolean =
        noteQueries.deleteNote(id).awaitAsOne().let { it > 0 }

    override suspend fun getAllNotesForUser(id: UserId): List<NoteModel> =
        noteQueries.selectAllForUser(id).transformAsList(Note::toModel)

    override suspend fun getAllNotesForUserAsFlow(id: UserId): Flow<List<NoteModel>> =
        noteQueries.selectAllForUser(id).transformAsListFlow(Note::toModel)

    override suspend fun getAllNotesForGroup(id: GroupId): List<NoteModel> =
        noteQueries.selectAllForGroup(id).transformAsList(Note::toModel)

    override suspend fun getAllNotesForGroupAsFlow(id: GroupId): Flow<List<NoteModel>> =
        noteQueries.selectAllForGroup(id).transformAsListFlow(Note::toModel)


}