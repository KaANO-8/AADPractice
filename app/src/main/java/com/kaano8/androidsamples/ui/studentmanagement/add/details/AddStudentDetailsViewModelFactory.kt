package com.kaano8.androidsamples.ui.studentmanagement.add.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.repository.student.StudentRepository

class AddStudentDetailsViewModelFactory(private val studentRepository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStudentDetailsViewModel::class.java)) {
            return AddStudentDetailsViewModel(studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}