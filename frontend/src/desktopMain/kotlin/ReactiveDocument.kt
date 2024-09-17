import kotlinx.coroutines.flow.MutableStateFlow
import org.automerge.Document


class ReactiveDocument {
    private val document = Document()

    private val _text = MutableStateFlow("")

    
}