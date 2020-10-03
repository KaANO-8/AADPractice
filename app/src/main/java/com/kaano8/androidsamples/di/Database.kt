package com.kaano8.androidsamples.di

import android.content.Context
import com.kaano8.androidsamples.api.jokes.JokesService
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.repository.student.StudentRepository
import com.kaano8.androidsamples.repository.student.StudentRepositoryImpl

object Database {
    fun getStudentRepository(applicationContext: Context): StudentRepository {
        val studentDataSource = AppDatabase.getInstance(applicationContext).studentDao
        return StudentRepositoryImpl(studentDataSource, JokesService.create())
    }
}