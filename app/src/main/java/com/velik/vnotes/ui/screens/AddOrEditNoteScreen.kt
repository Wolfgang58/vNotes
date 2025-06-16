package com.velik.vnotes.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.velik.vnotes.data.Note
import com.velik.vnotes.ui.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditNoteScreen(
    navController: NavHostController,
    viewModel: NoteViewModel,
    noteId: Int?
) {
    val existingNote = viewModel.getNoteById(noteId)
    var title by remember { mutableStateOf(existingNote?.title ?: "") }
    var content by remember { mutableStateOf(existingNote?.content ?: "") }
    var previousNote by remember { mutableStateOf<Note?>(null) }

    val originalTitle = existingNote?.title ?: ""
    val originalContent = existingNote?.content ?: ""

    var showExitDialog by remember { mutableStateOf(false) }

    // 🔁 Geri tuşuna basıldığında
    BackHandler {
        if (title != originalTitle || content != originalContent) {
            showExitDialog = true
        } else {
            navController.popBackStack()
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Değişiklikler Kaydedilmedi") },
            text = { Text("Çıkmak istediğinize emin misiniz? Kaydedilmemiş değişiklikler kaybolacak.") },
            confirmButton = {
                TextButton(onClick = {
                    showExitDialog = false
                    navController.popBackStack()
                }) {
                    Text("Evet")
                }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) {
                    Text("Hayır")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (noteId == null) "Yeni Not" else "Notu Düzenle") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    if (previousNote == null) {
                        previousNote = Note(title = title, content = content)
                    }
                    title = it
                },
                label = { Text("Başlık") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = content,
                onValueChange = {
                    if (previousNote == null) {
                        previousNote = Note(title = title, content = content)
                    }
                    content = it
                },
                label = { Text("İçerik") },
                modifier = Modifier.fillMaxWidth().height(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(
                    onClick = {
                        previousNote?.let {
                            title = it.title
                            content = it.content
                            previousNote = null
                        }
                    },
                    enabled = previousNote != null
                ) {
                    Text("Geri Al")
                }

                Button(
                    onClick = {
                        if (title.isNotBlank() && content.isNotBlank()) {
                            if (noteId == null) {
                                viewModel.addNote(Note(title = title, content = content))
                            } else {
                                viewModel.updateNote(Note(id = noteId, title = title, content = content))
                            }
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Kaydet")
                }
            }
        }
    }
}
