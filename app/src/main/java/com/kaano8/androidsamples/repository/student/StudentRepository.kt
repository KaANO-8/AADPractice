package com.kaano8.androidsamples.repository.student

import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.database.student.relation.StudentWithDetails

interface StudentRepository {

    suspend fun addStudent(student: Student)

    fun getAllStudents(): LiveData<List<Student>>

    suspend fun addStudentDetails(studentDetails: StudentDetails)

    fun getStudentWithDetails(studentId: Long): LiveData<StudentWithDetails>
}