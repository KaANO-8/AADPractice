package com.kaano8.androidsamples.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.kaano8.androidsamples.workmanager.SeedDatabaseWorker

@Database(entities = [Note::class, Gift::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val noteDatabaseDao: NoteDatabaseDao

    abstract val giftDao: GiftDatabaseDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AppDatabase

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (!this::INSTANCE.isInitialized) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "note_database").addCallback(object : Callback() {
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                val loadDatabaseRequest = OneTimeWorkRequest.from(SeedDatabaseWorker::class.java)
                                WorkManager.getInstance(context).enqueue(loadDatabaseRequest)
                            }
                        })
                            .fallbackToDestructiveMigration().build()
                }
                return INSTANCE
            }
        }
    }
}
