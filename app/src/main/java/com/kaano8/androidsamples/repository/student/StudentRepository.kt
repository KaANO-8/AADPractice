package com.kaano8.androidsamples.repository.student

import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.course.Course
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.database.student.relation.StudentCourseCrossRef
import com.kaano8.androidsamples.database.student.relation.StudentWIthCourses
import com.kaano8.androidsamples.database.student.relation.StudentWithDetails

interface StudentRepository {

    suspend fun addStudent(student: Student): Long

    suspend fun updateStudent(student: Student)

    fun getAllStudents(): LiveData<List<Student>>

    suspend fun addStudentDetails(studentDetails: StudentDetails)

    fun getStudentWithDetails(studentId: Long): LiveData<StudentWithDetails>

    fun getCourses(): LiveData<List<Course>>

    suspend fun insertSelectedCourses(selectedCourses: List<StudentCourseCrossRef>)

    fun getStudentWithCourses(studentId: Long): LiveData<StudentWIthCourses>

    suspend fun getAJoke()

    suspend fun clearDatabase()
}