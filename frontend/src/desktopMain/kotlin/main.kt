import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.automerge.Document
import org.automerge.ObjectId
import org.automerge.ObjectType
import org.jetbrains.compose.ui.tooling.preview.Preview

fun main() {

    val doc = Document()
    val transaction = doc.startTransaction()
    transaction.set(ObjectId.ROOT, "Text", ObjectType.TEXT)
    transaction.commit()

    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "DefaultKmpProject",
        ) {

        }
    }
}


@Preview
@Composable
fun Test(modifier: Modifier = Modifier) {
    Box(Modifier.size(50.dp).background(Color.Green))
}