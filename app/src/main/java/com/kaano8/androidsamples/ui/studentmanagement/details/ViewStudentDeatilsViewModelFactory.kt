package com.kaano8.androidsamples.ui.studentmanagement.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.repository.student.StudentRepository

class ViewStudentDetailsViewModelFactory(private val studentRepository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewStudentDetailsViewModel::class.java)) {
            return ViewStudentDetailsViewModel(studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}