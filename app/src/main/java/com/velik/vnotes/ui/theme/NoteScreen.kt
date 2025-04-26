package com.velik.vnotes.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.velik.vnotes.data.Note

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NoteScreen(viewModel: NoteViewModel, navController: NavHostController) {
    val notes by viewModel.notes.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("VNotes") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("İçerik") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        viewModel.addNote(Note(title = title, content = content))
                        title = ""
                        content = ""
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Ekle")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(notes) { note ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                navController.navigate("edit/${note.id}")
                            }
                    ) {

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(note.title, style = MaterialTheme.typography.titleMedium)
                                    Text(note.content, style = MaterialTheme.typography.bodyMedium)
                                }

                                IconButton(onClick = { viewModel.deleteNote(note) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Notu Sil"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
