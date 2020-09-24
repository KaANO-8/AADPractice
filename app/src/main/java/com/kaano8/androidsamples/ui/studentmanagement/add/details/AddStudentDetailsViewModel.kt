package com.kaano8.androidsamples.ui.studentmanagement.add.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.repository.student.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentDetailsViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    private val _navigateToStudentList = MediatorLiveData<Boolean>()
    val navigateToStudentList: LiveData<Boolean?>
        get() = _navigateToStudentList

    fun addStudentDeatils(studentId: Long, address: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.addStudentDetails(StudentDetails(studentId = studentId, address = address, phoneNumber = phoneNumber))
        }
        _navigateToStudentList.value = true
    }

    fun doneNavigation() {
        _navigateToStudentList.value = null
    }
}