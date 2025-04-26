package com.velik.vnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.velik.vnotes.ui.*
import com.velik.vnotes.ui.theme.AddNoteScreen
import com.velik.vnotes.ui.theme.NoteListScreen
import com.velik.vnotes.ui.theme.VNotesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VNotesTheme {
                val navController = rememberNavController()
                val viewModel = NoteViewModel(application)

                NavHost(navController = navController, startDestination = "list") {
                    composable("list") {
                        NoteListScreen(viewModel, navController)
                    }
                    composable("add") {
                        AddNoteScreen(viewModel, navController)
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