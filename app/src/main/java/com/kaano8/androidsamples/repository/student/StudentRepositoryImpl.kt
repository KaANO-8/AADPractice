package com.kaano8.androidsamples.repository.student

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.StudentDao
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.database.student.relation.StudentWithDetails

class StudentRepositoryImpl(private val studentDao: StudentDao): StudentRepository {

    override suspend fun addStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    override fun getAllStudents(): LiveData<List<Student>> = studentDao.getAllStudents()

    override suspend fun addStudentDetails(studentDetails: StudentDetails) {
        studentDao.insertStudentDetails(studentDetails)
    }

    override fun getStudentWithDetails(studentId: Long): LiveData<StudentWithDetails> = studentDao.getStudentWithDetails(studentId)
}