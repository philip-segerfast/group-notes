package com.example.groupnotes

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
import com.example.groupnotes.ui.theme.GroupNotesTheme
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

@Preview(showBackground = true)
@Composable
fun GroupsScreenPreview() {
    val groups = listOf(
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
        Group(0, "asdf", 0),
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)) {
        GroupsScreen(groups = groups)
    }
}

@Composable
fun GroupsScreen(groups: List<Group>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Fixed(2)
    ) {
        items(groups) { group ->
            GroupCard(group = group)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupCardPreview() {
    val group = Group(0, "Grupp 1", 0)
    Box(
        Modifier
            .size(250.dp)
            .padding(32.dp)) {
        GroupCard(group = group)
    }
}

@Composable
fun GroupCard(group: Group) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(120.dp)
            .padding(16.dp)
            .shadow(8.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White)
        ,
        contentAlignment = Alignment.Center
    ) {
        Text(group.name ?: "?")
    }
}
