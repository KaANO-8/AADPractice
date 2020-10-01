package com.kaano8.androidsamples.repository.student

import android.util.Log
import androidx.lifecycle.LiveData
import com.kaano8.androidsamples.api.jokes.JokesService
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.StudentDao
import com.kaano8.androidsamples.database.student.course.Course
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.database.student.relation.StudentCourseCrossRef
import com.kaano8.androidsamples.database.student.relation.StudentWIthCourses
import com.kaano8.androidsamples.database.student.relation.StudentWithDetails

class StudentRepositoryImpl(private val studentDao: StudentDao, private val jokesService: JokesService): StudentRepository {

    override suspend fun addStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    override fun getAllStudents(): LiveData<List<Student>> = studentDao.getAllStudents()

    override suspend fun addStudentDetails(studentDetails: StudentDetails) {
        studentDao.insertStudentDetails(studentDetails)
    }

    override fun getStudentWithDetails(studentId: Long): LiveData<StudentWithDetails> = studentDao.getStudentWithDetails(studentId)

    override fun getCourses(): LiveData<List<Course>> = studentDao.getAllCourses()

    override suspend fun insertSelectedCourses(selectedCourses: List<StudentCourseCrossRef>) {
        studentDao.insertStudentCourse(selectedCourses)
    }

    override fun getStudentWithCourses(studentId: Long): LiveData<StudentWIthCourses> = studentDao.getStudentWithCourses(studentId)


    override suspend fun getAJoke() {
        try {
            val joke = jokesService.getAJoke()
            val student = Student(firstName = joke.setup, lastName = joke.delivery, currentClass = joke.category)
            studentDao.insertStudent(student)
        } catch (exception: Exception) {
            Log.d("Exception occurred", exception.message.toString())
        }
    }
}