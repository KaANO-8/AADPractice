package com.kaano8.androidsamples.database.student.details

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class StudentDetails(
    @PrimaryKey(autoGenerate = true) val studentDetailsId: Long = 0,
    val studentId: Long,
    val address: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String
)