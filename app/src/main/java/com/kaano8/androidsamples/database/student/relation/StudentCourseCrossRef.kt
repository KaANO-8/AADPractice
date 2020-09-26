package com.kaano8.androidsamples.database.student.relation

import androidx.room.Entity

@Entity(primaryKeys = ["studentId", "courseId"], tableName = "student_course_cross_ref")
data class StudentCourseCrossRef(
    val studentId: Long,
    val courseId: Long
)