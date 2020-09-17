package com.kaano8.androidsamples.repository

import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.database.Gift
import com.kaano8.androidsamples.database.GiftDatabaseDao
import com.kaano8.androidsamples.database.Note
import com.kaano8.androidsamples.database.NoteDatabaseDao

class NoteRepository(private val noteDatabaseDao: NoteDatabaseDao, private val giftDatabaseDao: GiftDatabaseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val notes: LiveData<List<Note>> = noteDatabaseDao.getAllNotes()

    val gifts: LiveData<List<Gift>> = giftDatabaseDao.getGifts()

    suspend fun insert(note: Note) {
        noteDatabaseDao.insert(note)
    }

    suspend fun delete(note: Note) {
        noteDatabaseDao.deleteNote(note)
    }

    suspend fun update(note: Note) {
        noteDatabaseDao.update(note)
    }

    suspend fun clear() {
        noteDatabaseDao.clear()
    }

    fun getNoteById(noteId: Long): LiveData<Note> = noteDatabaseDao.getNoteById(noteId)
}