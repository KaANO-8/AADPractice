package com.kaano8.androidsamples.repository

import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.database.Note
import com.kaano8.androidsamples.database.NoteDatabaseDao

class NoteRepository(private val noteDatabaseDao: NoteDatabaseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val notes: LiveData<List<Note>> = noteDatabaseDao.getAllNotes()

    suspend fun insert(note: Note) {
        noteDatabaseDao.insert(note)
    }
}