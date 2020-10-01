package com.kaano8.androidsamples.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.repository.note.NoteRepository

class HomeViewModelFactory(private val noteRepository: NoteRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
