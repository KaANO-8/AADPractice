package com.kaano8.androidsamples.ui.studentmanagement.add.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaano8.androidsamples.database.student.course.Course
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.database.student.relation.StudentCourseCrossRef
import com.kaano8.androidsamples.models.CourseListItem
import com.kaano8.androidsamples.models.toCourseItem
import com.kaano8.androidsamples.repository.student.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentDetailsViewModel(private val studentRepository: StudentRepository) : ViewModel() {

    private val _navigateToStudentList = MutableLiveData<Boolean>()
    val navigateToStudentList: LiveData<Boolean?>
        get() = _navigateToStudentList

    val courseList: LiveData<List<Course>> = studentRepository.getCourses()

    fun addStudentDeatils(studentId: Long, address: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.addStudentDetails(StudentDetails(studentId = studentId, address = address, phoneNumber = phoneNumber))
        }
        _navigateToStudentList.value = true
    }

    fun doneNavigation() {
        _navigateToStudentList.value = null
    }

    fun insertSelectedCourseByStudent(courseList: List<CourseListItem>, studentId: Long) {
        val selectedCourses = courseList.filter { it.isSelected }.map { StudentCourseCrossRef(studentId = studentId, courseId = it.courseId)}
        viewModelScope.launch(Dispatchers.IO) {
            studentRepository.insertSelectedCourses(selectedCourses)
        }
    }
}