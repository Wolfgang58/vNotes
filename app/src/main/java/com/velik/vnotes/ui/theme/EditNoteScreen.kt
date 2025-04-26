package com.velik.vnotes.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.velik.vnotes.data.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteScreen(viewModel: NoteViewModel, noteId: Int?, navController: NavController) {
    val notes = viewModel.notes.collectAsState().value
    val note = notes.find { it.id == noteId }

    if (note == null) {
        Text("Not bulunamadı")
        return
    }

    // 🔥 FIXED: Don't bind directly to note.title in rememberSaveable
    val initialTitle = remember(noteId) { note.title }
    val initialContent = remember(noteId) { note.content }

    var title by rememberSaveable(noteId) { mutableStateOf(initialTitle) }
    var content by rememberSaveable(noteId) { mutableStateOf(initialContent) }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Notu Düzenle") })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            Text(
                text = "Başlık",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = title,
                onValueChange = { title = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 18.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "İçerik",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextField(
                value = content,
                onValueChange = { content = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 17.sp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank() && content.isNotBlank()) {
                    viewModel.updateNote(
                        Note(
                            id = note.id,
                            title = title,
                            content = content
                        )
                    )
                    navController.popBackStack()
                }
            }, modifier = Modifier.align(Alignment.End)) {
                Text("Güncelle")
            }
        }
    }
}


