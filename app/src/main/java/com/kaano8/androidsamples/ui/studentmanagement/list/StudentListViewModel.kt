package com.kaano8.androidsamples.ui.studentmanagement.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.repository.student.StudentRepository

class StudentListViewModel(private val studentRepository: StudentRepository): ViewModel() {

    val studentsList: LiveData<List<Student>> = studentRepository.getAllStudents()
}