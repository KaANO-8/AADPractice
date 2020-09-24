package com.kaano8.androidsamples.database.student.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.database.student.details.StudentDetails

data class StudentWithDetails(
    @Embedded val student: Student,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "studentId"
    ) val studentDetails: StudentDetails?
)