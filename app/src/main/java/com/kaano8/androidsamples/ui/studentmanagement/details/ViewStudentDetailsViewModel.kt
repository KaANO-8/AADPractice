package com.kaano8.androidsamples.ui.studentmanagement.details

import androidx.lifecycle.ViewModel
import com.kaano8.androidsamples.repository.student.StudentRepository

class ViewStudentDetailsViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    fun getStudentWithDetails(studentId: Long) = studentRepository.getStudentWithDetails(studentId)

}