package com.kaano8.androidsamples.ui.studentmanagement.unlock.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.ui.studentmanagement.add.AddStudentFragment.Companion.STUDENT_ID_KEY

class UnlockWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val database = AppDatabase.getInstance(applicationContext)
        val studentId = inputData.getLong(STUDENT_ID_KEY, -1)

        if (studentId == -1L)
            return Result.failure()

        val student = database.studentDao.getStudent(studentId)

        student?.copy(currentClass = UPDATED_CLASS)?.let {
            database.studentDao.updateStudent(it)
        }

        return Result.success()

    }

    companion object {
        private const val UPDATED_CLASS = "0"
    }
}