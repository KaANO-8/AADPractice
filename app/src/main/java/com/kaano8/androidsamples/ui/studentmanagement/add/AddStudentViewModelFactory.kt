package com.kaano8.androidsamples.ui.studentmanagement.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.repository.student.StudentRepository

class AddStudentViewModelFactory(private val studentRepository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddStudentViewModel::class.java)) {
            return AddStudentViewModel(studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}