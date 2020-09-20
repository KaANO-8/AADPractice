package com.kaano8.androidsamples.ui.studentmanagement.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kaano8.androidsamples.repository.student.StudentRepository

class StudentListViewModelFactory(private val studentRepository: StudentRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentListViewModel::class.java)) {
            return StudentListViewModel(studentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}