package com.velik.vnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.velik.vnotes.ui.NoteViewModel
import com.velik.vnotes.ui.screens.AddOrEditNoteScreen
import com.velik.vnotes.ui.screens.NoteListScreen
import com.velik.vnotes.ui.theme.VNotesTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VNotesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "list") {
                        composable("list") {
                            NoteListScreen(viewModel = viewModel, navController = navController)
                        }
                        composable("add") {
                            AddOrEditNoteScreen(
                                viewModel = viewModel,
                                navController = navController,
                                noteId = null // yeni not için
                            )
                        }

                        composable("edit/{noteId}") { backStackEntry ->
                            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
                            AddOrEditNoteScreen(
                                viewModel = viewModel,
                                navController = navController,
                                noteId = noteId
                            )
                        }

                    }
                }
            }
        }
    }
}
