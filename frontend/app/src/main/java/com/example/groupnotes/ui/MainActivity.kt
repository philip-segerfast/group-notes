package com.example.groupnotes.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.groupnotes.api.Group
import com.example.groupnotes.apiService
import com.example.groupnotes.ui.compose.GroupsScreen
import com.example.groupnotes.ui.compose.theme.GroupNotesTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val TAG = this::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var groups by remember { mutableStateOf(emptyList<Group>()) }
            var string by remember { mutableStateOf<String?>(null) }

            LaunchedEffect(Unit) {
                withContext(Dispatchers.IO) {
                    launch {
//                        apiService
//                            .getAllGroups()
//                            .asFlow()
//                            .collect { storedGroups ->
//                                Log.d(TAG, "New groups: $storedGroups")
//                                groups = storedGroups.groups
//                            }
                    }

                    launch {
                        apiService
                            .getAllStrings()
                            .asFlow()
                            .collect { newString ->
                                Log.d(TAG, "New string: $newString")
                                string = newString.contents
                            }
                    }
                }
            }

            GroupNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        Text("String: \"$string\"")
                        GroupsScreen(groups = groups)
                    }
                }
            }
        }
    }
}
