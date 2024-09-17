package org.robphi.groupnotes

import org.automerge.Document
import org.automerge.ObjectId
import org.automerge.ObjectType

fun test() {

    val document = Document()
    val transaction = document.startTransaction()
    transaction.set(ObjectId.ROOT, "Text", ObjectType.TEXT)
    transaction.commit()


    /*********************/


}