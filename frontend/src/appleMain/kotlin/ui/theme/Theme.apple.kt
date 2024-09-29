package ui.theme

import androidx.compose.runtime.Composable

@Composable
actual fun GroupNotesTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    content()
}