package com.kaano8.androidsamples.ui.home

import androidx.lifecycle.*
import com.kaano8.androidsamples.database.Note
import com.kaano8.androidsamples.database.NoteDatabaseDao
import com.kaano8.androidsamples.repository.NoteRepository
import kotlinx.coroutines.*

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = noteRepository.notes

    fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insert(note)
        }
    }
}