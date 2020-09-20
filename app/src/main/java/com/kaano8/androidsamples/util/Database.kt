package com.kaano8.androidsamples.util.extensions

import android.app.Application
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.repository.student.StudentRepository
import com.kaano8.androidsamples.repository.student.StudentRepositoryImpl

object Database {
    fun getStudentRepository(application: Application): StudentRepository {
        val studentDataSource = AppDatabase.getInstance(application).studentDao
        return StudentRepositoryImpl(studentDataSource)
    }
}