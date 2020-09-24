package com.kaano8.androidsamples.database.student.course

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Course(
    @PrimaryKey val courseId: Long,
    @ColumnInfo(name = "course_name") val courseName: String
)