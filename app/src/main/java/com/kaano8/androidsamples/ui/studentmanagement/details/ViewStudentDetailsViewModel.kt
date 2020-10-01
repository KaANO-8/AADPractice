package com.kaano8.androidsamples.ui.studentmanagement.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.repository.student.StudentRepository
import kotlinx.coroutines.launch

class ViewStudentDetailsViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    val updateResult = MutableLiveData<String>()

    fun getStudentWithDetails(studentId: Long) = studentRepository.getStudentWithDetails(studentId)

    fun getStudentWIthCourses(studentId: Long) = studentRepository.getStudentWithCourses(studentId)

    fun updateStudentDetails(studentId: Long) {
        val student = Student(studentId = studentId, firstName = "Rahul", lastName = "Kanojia", currentClass = "12")
        viewModelScope.launch {
            studentRepository.updateStudent(student = student)
        }
    }
}