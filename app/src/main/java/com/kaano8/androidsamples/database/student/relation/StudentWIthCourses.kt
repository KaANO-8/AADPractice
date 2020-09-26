package com.kaano8.androidsamples.database.student.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.course.Course


data class StudentWIthCourses(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "courseId",
        associateBy = Junction(StudentCourseCrossRef::class)
    ) val courses: List<Course>
)