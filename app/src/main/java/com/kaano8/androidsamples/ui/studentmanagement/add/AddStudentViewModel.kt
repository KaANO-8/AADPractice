package com.kaano8.androidsamples.ui.studentmanagement.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.repository.student.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    private val _navigateToStudentList = MediatorLiveData<Boolean>()
    val navigateToStudentList: LiveData<Boolean?>
        get() = _navigateToStudentList

    fun addStudent(firstName: String, lastName: String, currentClass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.addStudent(Student(firstName = firstName, lastName = lastName, currentClass = currentClass))
        }
        _navigateToStudentList.value = true
    }

    fun doneNavigation() {
        _navigateToStudentList.value = null
    }
}