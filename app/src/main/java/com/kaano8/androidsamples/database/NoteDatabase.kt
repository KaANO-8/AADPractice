package com.kaano8.androidsamples.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Note::class], exportSchema = false, version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract val noteDatabaseDao: NoteDatabaseDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: NoteDatabase

        fun getInstance(context: Context): NoteDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE =
                        Room.databaseBuilder(context, NoteDatabase::class.java, "note_database")
                            .fallbackToDestructiveMigration().build()
                }
                return INSTANCE
            }
        }
    }
}
