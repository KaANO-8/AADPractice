package com.kaano8.androidsamples.database.note

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaano8.androidsamples.models.note.Note

@Dao
interface NoteDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note): Long

    @Update
    fun update(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM thank_you_note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("DELETE FROM thank_you_note_table")
    fun clear()

    @Query("SELECT * FROM thank_you_note_table WHERE noteId = :noteId")
    fun getNoteById(noteId: Long): LiveData<Note>
}