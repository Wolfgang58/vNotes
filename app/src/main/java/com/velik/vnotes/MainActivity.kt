package com.velik.vnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.velik.vnotes.ui.NoteScreen
import com.velik.vnotes.ui.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: NoteViewModel = viewModel()
            NoteScreen(viewModel)
        }
    }
}
