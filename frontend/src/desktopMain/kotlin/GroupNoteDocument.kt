import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.automerge.AmValue
import org.automerge.ChangeHash
import org.automerge.Document
import org.automerge.ObjectId
import org.automerge.ObjectType
import org.automerge.Transaction
import kotlin.jvm.optionals.getOrNull

class GroupNoteDocument(private val document: Document) {

    private val noteObjectId = getOrCreateNoteTextObjectId()

    private val _noteValueFlow = MutableStateFlow("")

    private val noteKey = "note"

    sealed interface DocumentKey {
        val key: String

        sealed interface StringKey : DocumentKey {
            data object Note : StringKey {
                override val key: String = "note"
            }
        }
    }

    private fun getOrCreateNoteTextObjectId(): ObjectId {
        val note = document.get(ObjectId.ROOT, noteKey).getOrNull() as? AmValue.Text
        if(note != null) return note.id

        document.commitTransaction {
            set(ObjectId.ROOT, noteKey, ObjectType.TEXT)
        }
        return getNote()!!.id
    }

    private fun writeAndNotifyChange(objectId: ObjectId, value: AmValue) {
        document.commitTransaction {

        }
    }

    private fun getNote(): AmValue.Text? =
        document.get(ObjectId.ROOT, noteKey).getOrNull() as? AmValue.Text

    fun collectNote(): Flow<String> {
        TODO()
    }

}

fun Document.commitTransaction(block: Transaction.() -> Unit): ChangeHash? {
    val transaction = startTransaction().also(block)
    return transaction.commit().getOrNull()
}