import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.forEachTextValue
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.automerge.Document

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutomergeTextField(
    modifier: Modifier = Modifier
) {
    val document = remember { GroupNoteDocument(Document()) }

    val state = rememberTextFieldState("")

    LaunchedEffect(Unit) {
        state.forEachTextValue { text ->
            
        }
    }

    BasicTextField2(state)
}