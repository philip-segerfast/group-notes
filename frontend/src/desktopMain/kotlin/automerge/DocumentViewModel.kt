package automerge

import androidx.lifecycle.ViewModel
import model.NoteId

class DocumentViewModel(
    private val noteId: NoteId,
) : ViewModel() {

//    private val documentApi: DocumentAPI = DocumentApi(RSocketDocumentAPI())

    fun getUpdates() {
//        return documentRepository
    }

}

