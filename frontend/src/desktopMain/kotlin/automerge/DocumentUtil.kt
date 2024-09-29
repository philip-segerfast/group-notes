package automerge

import org.automerge.AmValue
import org.automerge.Document
import org.automerge.ObjectId
import org.automerge.ObjectType
import kotlin.jvm.optionals.getOrNull

/**
 * This file is for testing the Kotlin Automerge library. It generates a document with lots of data and nested objects.
 * It then prints the document and its byte representation to the console.
 * */
fun main(vararg args: String) {
    val document = Document()
    document.startTransaction().apply {
        set(ObjectId.ROOT, "text", ObjectType.TEXT)
    }.commit()

    val documentBytes = document.save()!!
    println("Document: $document")
    val byteArrayString = documentBytes.toList().chunked(20).let { chunks ->
        chunks.map { chunk ->
            chunk.joinToString(separator = "") { "$it, " }
        }.joinToString(
            separator = System.lineSeparator(),
            prefix = "byteArrayOf(${System.lineSeparator()}",
            postfix = "${System.lineSeparator()})",
        ) {
            "\t$it"
        }
    }
    println(byteArrayString)

    val tree = document.nodeTree()
    println(tree.nodeString())
}

sealed interface DocumentNode {
    data class ValueNode(val value: AmValue): DocumentNode
    data class TextNode(override val objectId: ObjectId, val value: String): ObjectIdNode
    data class ListNode(override val objectId: ObjectId, val values: List<DocumentNode>):
        ObjectIdNode
    data class MapNode(override val objectId: ObjectId, val map: Map<String, DocumentNode>):
        ObjectIdNode
    sealed interface ObjectIdNode: DocumentNode {
        val objectId: ObjectId
    }
}

fun DocumentNode.nodeString(level: Int = 0): String = when(this) {
    is DocumentNode.ListNode -> buildString {
        appendLine("[")
        values.map { it.nodeString(level) }.forEachIndexed { index, item ->
            val comma = if(index == values.lastIndex) "" else ","
            appendLine("${getTabs(level + 1)}$item$comma")
        }
        append("${getTabs(level)}]")
    }
    is DocumentNode.MapNode -> buildString {
        appendLine("\"$objectId\": {")
        map.entries.forEachIndexed { index, (key, value) ->
            val comma = if(index == map.size - 1) "" else ","
            appendLine("${getTabs(level + 1)}\"$key\": ${value.nodeString(level + 1)}$comma")
        }
        append("${getTabs(level)}}")
    }
    is DocumentNode.ValueNode -> value.stringValue()
    is DocumentNode.TextNode -> "\"$value\""
}

fun getTabs(level: Int): String = buildString {
    repeat(level) {
        append("\t")
    }
}

fun Document.nodeTree(): DocumentNode.MapNode {
    val rootMapEntries = this.mapEntries(ObjectId.ROOT).getOrNull()!!
    val map = rootMapEntries.associate { entry ->
        val key = entry.key
        val value = entry.value
        key to value.toDocumentNode(this)
    }
    return DocumentNode.MapNode(ObjectId.ROOT, map)
}

fun AmValue.toDocumentNode(document: Document): DocumentNode = when (this) {
    is AmValue.Map -> {
        val objectId = this.id
        val entries = document.mapEntries(objectId).getOrNull()!!
        val map = entries.associate { entry ->
            val key = entry.key
            val value = entry.value
            key to value.toDocumentNode(document)
        }
        DocumentNode.MapNode(objectId, map)
    }

    is AmValue.List -> {
        val objectId = this.id
        val items = document.listItems(objectId).getOrNull()!!
        DocumentNode.ListNode(objectId, items.map { it.toDocumentNode(document) })
    }

    is AmValue.Text -> {
        val objectId = this.id
        val text = document.text(objectId).getOrNull()!!
        DocumentNode.TextNode(objectId, text)
    }

    else -> DocumentNode.ValueNode(this)
}


fun AmValue.stringValue(): String {
    val value = when(this) {
        is AmValue.UInt -> "${this.value}"
        is AmValue.Int -> "${this.value}"
        is AmValue.Bool -> "${this.value}"
        is AmValue.Bytes -> "${this.value}"
        is AmValue.Str -> "\"${this.value}\""
        is AmValue.F64 -> "${this.value}"
        is AmValue.Counter -> "${this.value}"
        is AmValue.Timestamp -> "${this.value}"
        is AmValue.Null -> null
        is AmValue.Unknown -> "${this.value.toList()}"
        is AmValue.Map -> "${this.id}"
        is AmValue.List -> "${this.id}"
        is AmValue.Text -> "${this.id}"
        else -> error("Unsupported AmValue: ${this::class.qualifiedName}")
    }
    return "[${ this::class.simpleName }]: $value"
}