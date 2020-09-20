package com.kaano8.androidsamples.repository.student

import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.database.student.Student

interface StudentRepository {

    suspend fun addStudent(student: Student)

    fun getAllStudents(): LiveData<List<Student>>
}