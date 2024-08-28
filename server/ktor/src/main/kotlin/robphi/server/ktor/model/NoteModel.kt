package robphi.server.ktor.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

typealias NoteEntity = robphi.server.ktor.database.Note

typealias NoteId = Int

@Serializable
data class NoteModel(
    val id: NoteId,
    val title: String,
    val content: String,
    val timestamp: LocalDateTime,
    val groupId: GroupId,
    val creator: Int
)

fun NoteEntity.toModel() = NoteModel(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp.toKotlinLocalDateTime(),
    groupId = groupId,
    creator = creator
)

fun NoteModel.Companion.fromJson(json: String) = Json.decodeFromString<NoteModel>(json)

fun NoteModel.toJson() = Json.encodeToString(this)
