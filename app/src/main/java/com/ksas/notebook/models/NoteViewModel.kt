package com.ksas.notebook.models

import android.app.Application
import androidx.lifecycle.*
import com.ksas.notebook.database.NoteDao
import com.ksas.notebook.database.NoteDatabase
import com.ksas.notebook.database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepo: NotesRepository

    val allNotes: LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        noteRepo = NotesRepository(dao)
        allNotes = noteRepo.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.insert(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepo.update(note)
    }

}