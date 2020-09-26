package com.kaano8.androidsamples.database.student

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kaano8.androidsamples.database.student.course.Course
import com.kaano8.androidsamples.database.student.details.StudentDetails
import com.kaano8.androidsamples.database.student.relation.StudentCourseCrossRef
import com.kaano8.androidsamples.database.student.relation.StudentWIthCourses
import com.kaano8.androidsamples.database.student.relation.StudentWithDetails

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Query("Select * from Student")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("Select * from Student where studentId = :studentId")
    fun getStudent(studentId: Long): LiveData<Student>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentDetails(studentDetails: StudentDetails)

    @Transaction
    @Query("Select * from Student where studentId = :studentId")
    fun getStudentWithDetails(studentId: Long): LiveData<StudentWithDetails>

    @Insert
    fun insertAllCourses(courses: List<Course>)

    @Query("Select * from Course")
    fun getAllCourses(): LiveData<List<Course>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudentCourse(courseListSelectedByStudent: List<StudentCourseCrossRef>)

    @Query("Select * from Student where studentId = :studentId")
    fun getStudentWithCourses(studentId: Long): LiveData<StudentWIthCourses>
}