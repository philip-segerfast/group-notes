package com.example.groupnotes.automerge

import com.example.groupnotes.ui.compose.screen.groupscreen.Note
import org.automerge.Document
import org.automerge.ObjectId
import org.automerge.ObjectType
import kotlin.jvm.optionals.getOrNull

class NoteDocument(
    private val note: Note,
    private val actorId: ByteArray,
    val onPushChanges: () -> Unit,
) {
    private var document = Document(actorId)

    init {
        val doc = Document(actorId)
        doc.startTransaction()
    }

    fun writeText(str: String, index: Long) {
        val (text, result) = document.startTransaction().use { transaction ->
            val text = transaction.set(ObjectId.ROOT, "text", ObjectType.TEXT)
            transaction.spliceText(text, index, 0, str)
            text to transaction.commit().getOrNull()
        }
    }

    fun applyExternalDocChanges(externalDoc: Document) {
        document.merge(externalDoc)
    }

}