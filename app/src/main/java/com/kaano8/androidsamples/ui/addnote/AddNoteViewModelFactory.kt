package com.kaano8.androidsamples.ui.addnote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.database.NoteDatabaseDao

@Suppress("UNCHECKED_CAST")
class AddNoteViewModelFactory(
    private val dataSource: NoteDatabaseDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddNoteViewModel::class.java)) {
            return AddNoteViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}