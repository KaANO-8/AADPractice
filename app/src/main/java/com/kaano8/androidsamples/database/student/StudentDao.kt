package com.kaano8.androidsamples.database.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudent(student: Student)

    @Query("Select * from Student")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("Select * from Student where studentId = :studentId")
    fun getStudent(studentId: Long): LiveData<Student>

}