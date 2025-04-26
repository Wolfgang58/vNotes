package com.velik.vnotes.ui.theme

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
import com.velik.vnotes.ui.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(viewModel: NoteViewModel, navController: NavController) {
    var title by rememberSaveable { mutableStateOf("") }
    var content by rememberSaveable { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Yeni Not") })
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
                text = "Not",
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
                    viewModel.addNote(Note(title = title, content = content))
                    navController.popBackStack()
                }
            }, modifier = Modifier.align(Alignment.End)) {
                Text("Kaydet")
            }
        }
    }
}