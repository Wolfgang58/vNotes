package com.velik.vnotes.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.velik.vnotes.data.Note
import com.velik.vnotes.data.NoteDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    // 🔍 Arama çubuğu için query durumu
    val searchQuery = MutableStateFlow("")

    // 🔍 Aramaya göre filtrelenmiş notlar
    @OptIn(ExperimentalCoroutinesApi::class)
    val filteredNotes = searchQuery
        .flatMapLatest { query ->
            noteDao.getAllNotes().map { notes ->
                if (query.isBlank()) notes
                else notes.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.content.contains(query, ignoreCase = true)
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 📝 Not ekleme
    fun addNote(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    // 📝 Not silme
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note.copy(updatedAt = System.currentTimeMillis()))
        }
    }

    // 📝 Not güncelleme
    fun updateNote(note: Note) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    // 📝 Not ID ile alma (edit ekranı için)
    fun getNoteById(id: Int?): Note? {
        return filteredNotes.value.find { it.id == id }
    }
}
