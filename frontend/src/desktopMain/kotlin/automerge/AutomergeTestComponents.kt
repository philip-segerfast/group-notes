package automerge

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AutomergeTextField(
    modifier: Modifier = Modifier,
    automergeDocument: GroupNoteDocument, // Text which is stored in the Automerge document.
    onTextChange: (String) -> Unit
) {
    val localDocument = remember { GroupNoteDocument() }

    val textFieldState = rememberTextFieldState()
    val textFieldValue = textFieldState.text

    LaunchedEffect(textFieldValue) {
        delay(500.milliseconds)
//        onTextChange(text)
    }

    BasicTextField2(
        modifier = modifier,
        state = textFieldState,
    )
}














