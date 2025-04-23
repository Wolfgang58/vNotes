package com.velik.vnotes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.velik.vnotes.data.Note

@Composable
fun EditNoteScreen(
    viewModel: NoteViewModel,
    noteId: Int?,
    navController: NavController
) {
    val noteList by viewModel.notes.collectAsState()
    val note = noteList.find { it.id == noteId }

    if (note == null) {
        Text("Not bulunamadı veya yükleniyor...")
        return
    }

    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Notu Düzenle", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.updateNote(note.copy(title = title, content = content))
            navController.popBackStack()
        }) {
            Text("Güncelle")
        }
    }
}
