package com.kaano8.androidsamples.repository.student

import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.StudentDao

class StudentRepositoryImpl(private val studentDao: StudentDao): StudentRepository {

    override suspend fun addStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    override fun getAllStudents(): LiveData<List<Student>> = studentDao.getAllStudents()

}