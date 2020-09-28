package com.kaano8.androidsamples.ui.home

import androidx.lifecycle.*
import com.kaano8.androidsamples.models.note.Note
import com.kaano8.androidsamples.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = noteRepository.notes

    private val _clearDatabaseSnackBarEvent = MutableLiveData<Boolean>()

    val clearDatabaseSnackBarEvent: LiveData<Boolean>
        get() = _clearDatabaseSnackBarEvent

    fun clearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.clear()
        }
        _clearDatabaseSnackBarEvent.value = true
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.delete(note)
        }
    }

    /**
     * Call this immediately after calling `show()` on a toast.
     *
     * It will clear the toast request, so if the user rotates their phone it won't show a duplicate
     * toast.
     */
    fun doneShowingSnackbar() {
        _clearDatabaseSnackBarEvent.value = false
    }
}