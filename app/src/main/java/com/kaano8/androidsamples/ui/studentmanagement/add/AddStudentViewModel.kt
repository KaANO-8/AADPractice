package com.kaano8.androidsamples.ui.studentmanagement.add

import androidx.lifecycle.*
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.repository.student.StudentRepository
import com.kaano8.androidsamples.ui.studentmanagement.unlock.UnlockWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AddStudentViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    private val _navigateToStudentList = MediatorLiveData<Boolean>()
    val navigateToStudentList: LiveData<Boolean?>
        get() = _navigateToStudentList

    private val _scheduleWork = MutableLiveData<Pair<Long, Long>?>()
    val scheduleWork: LiveData<Pair<Long, Long>?>
        get() = _scheduleWork

    private var studentId: Long = -1L

    fun addStudent(firstName: String, lastName: String, currentClass: String) {
        viewModelScope.launch(Dispatchers.IO) {
            studentId = studentRepository.addStudent(Student(firstName = firstName, lastName = lastName, currentClass = currentClass))
            _scheduleWork.postValue((Pair(studentId, currentClass.toLong())))
        }
    }

    fun doneNavigation() {
        _navigateToStudentList.value = null
    }

    fun doneScheduling() {
        _scheduleWork.value = null
        _navigateToStudentList.value = true
    }

}