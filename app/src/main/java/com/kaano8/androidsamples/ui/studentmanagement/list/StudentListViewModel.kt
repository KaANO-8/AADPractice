package com.kaano8.androidsamples.ui.studentmanagement.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.repository.student.StudentRepository
import kotlinx.coroutines.launch

class StudentListViewModel(private val studentRepository: StudentRepository): ViewModel() {

    private val _spinnerVisibility = MutableLiveData<Boolean>()

    val spinnerVisibility: LiveData<Boolean>
    get() = _spinnerVisibility

    val studentsList: LiveData<List<Student>> = studentRepository.getAllStudents()

    fun insertAJoke() {
        viewModelScope.launch {
            _spinnerVisibility.value = true
            studentRepository.getAJoke()
            _spinnerVisibility.value = false
        }
    }
}