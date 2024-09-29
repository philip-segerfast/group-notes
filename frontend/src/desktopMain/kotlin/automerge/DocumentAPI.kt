package automerge

import decodeFromPayload
import encodeToPayload
import io.rsocket.kotlin.RSocket
import io.rsocket.kotlin.payload.Payload
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoBuf
import model.NoteId
import org.automerge.ChangeHash
import org.automerge.Document
import java.nio.ByteBuffer
import java.util.UUID

interface DocumentAPI {

    /** Fetch a document with the given ID from the provider. */
    suspend fun fetch(documentId: ActorId): Document

    /**
     * Used to tell the document provider the current state of the client's document.
     * */
    suspend fun pushHeads(documentId: ActorId, heads: List<ChangeHash>)

    /**
     * Stream the changes from the document provider. Will update depending on the latest pushed heads.
     * */
    fun collectDocumentChanges(documentId: ActorId): Flow<DocumentChanges>

}

fun NoteId.toActorId(): ActorId {
    val uuid = UUID.fromString("NoteId/${this}")
    return ActorId(uuid.toByteArray())
}

fun ByteArray.toUUID(): UUID {
    val bb: ByteBuffer = ByteBuffer.wrap(this)
    val firstLong: Long = bb.getLong()
    val secondLong: Long = bb.getLong()
    return UUID(firstLong, secondLong)
}

fun UUID.toByteArray(): ByteArray {
    val bb: ByteBuffer = ByteBuffer.allocate(16)
    bb.putLong(this.mostSignificantBits)
    bb.putLong(this.leastSignificantBits)
    return bb.array()
}

/** Acts as a server for the [DocumentAPI]. */
class FakeNoteDocumentServer: DocumentAPI {

    private val documents = mutableMapOf<ActorId, Document>()
//    private val documentSchematicProvider = DocumentSchematicProvider()

    override suspend fun fetch(documentId: ActorId): Document {
        TODO("Not yet implemented")
    }

    override suspend fun pushHeads(documentId: ActorId, heads: List<ChangeHash>) {
        TODO("Not yet implemented")
    }

    override fun collectDocumentChanges(documentId: ActorId): Flow<DocumentChanges> {
        TODO("Not yet implemented")
    }
}

@OptIn(ExperimentalSerializationApi::class)
class RSocketDocumentAPI(
    private val rSocket: RSocket,
    private val proto: ProtoBuf,
) : DocumentAPI {
    override suspend fun fetch(documentId: ActorId): Document {
        val payload: Payload = proto.encodeToPayload(route = "documents.fetch", FetchDocument(documentId))
        val response: Payload = rSocket.requestResponse(payload)
        val serialized: SerializedDocument = proto.decodeFromPayload(response)
        return Document.load(serialized.bytes)
    }

    override suspend fun pushHeads(documentId: ActorId, heads: List<ChangeHash>) {
        val payload = proto.encodeToPayload(route = "documents.pushHeads", PushHeads(heads.map { it.bytes }))
        val response = rSocket.requestResponse(payload)
        println("Response for pushHeads: $response")
    }

    override fun collectDocumentChanges(documentId: ActorId): Flow<DocumentChanges> {
        val payload = proto.encodeToPayload(route = "documents.changes", StreamDocumentChanges(documentId))
        return rSocket
            .requestStream(payload)
            .map { proto.decodeFromPayload(it) }
    }
}

abstract class DocumentSchematicProvider {

    abstract fun generateDocumentBytes(): ByteArray

    private var document: Document? = null

    /**
     * Create a document from a pre-defined byte array (shared between backend and clients).
     * This is originally created locally and then pushed to backend.
     * */
    fun createSchematic(): Document {
        if(document == null) document = Document.load(generateDocumentBytes())
        return document!!
    }
}

class NoteDocumentSchematicProvider : DocumentSchematicProvider() {
    override fun generateDocumentBytes() = byteArrayOf(
        -123, 111, 74, -125, -25, 2, -79, -10, 0, 110, 1, 16, -76, 4, -127, 17, 98, -68, 76, -34,
        -97, -83, -112, 77, 90, 66, 40, 107, 1, 73, 44, 81, 125, 53, -25, 104, -2, 23, -114, -22,
        -35, 4, 94, 42, -121, 79, -4, 102, -103, -122, -17, 114, -5, 108, -15, -50, 125, -82, 2, 48,
        59, 6, 1, 2, 3, 2, 19, 2, 35, 2, 64, 2, 86, 2, 7, 21, 6, 33, 2, 35,
        2, 52, 1, 66, 2, 86, 2, -128, 1, 2, 127, 0, 127, 1, 127, 1, 127, 0, 127, 0,
        127, 7, 127, 4, 116, 101, 120, 116, 127, 0, 127, 1, 1, 127, 4, 127, 0, 127, 0, 0,
    )
}

@Serializable
data class ActorId(val bytes: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActorId

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int = bytes.contentHashCode()
}

@Serializable
data class FetchDocument(val documentId: ActorId)

@Serializable
data class PushHeads(val heads: List<ChangeHashBytes>)

@Serializable
data class StreamDocumentChanges(val documentId: ActorId)

typealias ChangeHashBytes = ByteArray

@Serializable
data class SerializedDocument(
    /** The bytes for the document, meant to be loaded using [Document.load] */
    val bytes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SerializedDocument

        return bytes.contentEquals(other.bytes)
    }

    override fun hashCode(): Int = bytes.contentHashCode()
}

data class DocumentChanges(
    val heads: List<ChangeHash>,
    val changes: ByteArray,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DocumentChanges

        if (heads != other.heads) return false
        if (!changes.contentEquals(other.changes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = heads.hashCode()
        result = 31 * result + changes.contentHashCode()
        return result
    }
}