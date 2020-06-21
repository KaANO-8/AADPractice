package com.kaano8.androidsamples.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kaano8.androidsamples.database.Note
import com.kaano8.androidsamples.repository.NoteRepository

class HomeViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    val notes: LiveData<List<Note>> = noteRepository.notes
}