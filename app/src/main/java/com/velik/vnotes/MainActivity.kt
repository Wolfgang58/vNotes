package com.velik.vnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.velik.vnotes.ui.EditNoteScreen
import com.velik.vnotes.ui.NoteScreen
import com.velik.vnotes.ui.NoteViewModel
import com.velik.vnotes.ui.theme.VNotesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VNotesTheme { // ← BURAYI DÜZELT!
                val viewModel = NoteViewModel(application)
                val navController = rememberNavController()

                NavHost(navController, startDestination = "notes") {
                    composable("notes") {
                        NoteScreen(viewModel, navController)
                    }
                    composable("edit/{noteId}") { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
                        EditNoteScreen(viewModel, noteId, navController)
                    }
                }
            }
        }
    }
}
