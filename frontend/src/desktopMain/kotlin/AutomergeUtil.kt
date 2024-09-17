import org.automerge.AmValue
import org.automerge.AutomergeException
import org.automerge.Document
import org.automerge.MapEntry
import org.automerge.ObjectId
import org.automerge.ObjectType
import java.util.Optional
import kotlin.jvm.optionals.getOrNull


class AutomergeTextDocument() {
    private val document = Document()

    fun insertContent() {
        val rootMapEntries: Array<MapEntry>? = document.mapEntries(ObjectId.ROOT).getOrNull()

        val actorIdMapId: AmValue.Map? = document[ObjectId.ROOT, "actorIds"].getOrNull() as? AmValue.Map
        

        val transaction = document.startTransaction()
        // This text node is set in the document root. It can be changed using this objectId (instead of ObjectId.ROOT).
        val objectId: ObjectId = transaction.set(ObjectId.ROOT, "text", ObjectType.TEXT)
        val otherText = transaction.set(objectId, "Something else", ObjectType.TEXT)

        // Edit existing text node.
        val textObject = transaction.spliceText(ObjectId.ROOT, 0, 5, "Zup")

        // Set a map with "map" as key.
        val mapObject = transaction.set(ObjectId.ROOT, "map", ObjectType.MAP);

        // Operations: `set`, `splice`, `mark`, `insert`, `increment`, `delete`, `unmark`

        // set: Set a value in a list (by index) or a map (by key).
        // splice: Add and remove something from a list or text.
        // mark: Mark a range in a text for any purpose (for example highlighting, comments etc.)
        // insert: Insert something into a list or text at an index. Will move everything after to the right.
        // increment: Increment a counter in a list or a map.
        // delete: Delete a range in a list or map (or text?? - doesn't say).
        // unmark: Unmark a range in a text.

        // NOTE: The root of the document is a map.

        document.
    }

    /*
    * NOTES
    * val tx = document.startTransaction()
    * tx.spliceText()
    * */

}

interface TransactionMethods {

}