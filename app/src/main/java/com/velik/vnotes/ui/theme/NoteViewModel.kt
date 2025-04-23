package com.velik.vnotes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.velik.vnotes.data.Note
import com.velik.vnotes.data.NoteDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notes.asStateFlow()

    init {
        viewModelScope.launch {
            noteDao.getAllNotes().collect {
                _notes.value = it
            }
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }
    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }
}
