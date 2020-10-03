package com.kaano8.androidsamples.ui.studentmanagement.add

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.preference.PreferenceManager
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.ui.studentmanagement.add.AddStudentFragment.Companion.STUDENT_ID_KEY
import com.kaano8.androidsamples.di.Injector.getNotificationManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UnlockAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val isNotificationsTurnedOff = sharedPreferences.getBoolean("stopNotifications", false)

        if (!isNotificationsTurnedOff) {
            val notificationManager = getNotificationManager(context, UNLOCK_PRIMARY_ID, UNLOCK_CHANNEL_NAME, UNLOCK_CHANNEL_DESCRIPTION)
            // get database instance
            val database = AppDatabase.getInstance(context?.applicationContext!!)
            // get student key passed in index
            val data = intent?.getLongExtra(STUDENT_ID_KEY, -1) ?: -1

            val pendingIntent = getNotificationPendingIntent(context, data)

            /**
             * Not an ideal solution to call database query on broadcast receiver,
             * but currently I'm aware of this solution only.
             */
            GlobalScope.launch {
                // fetch data from database
                val student = database.studentDao.getStudent(data)

                student?.let {
                    val notification = createNotification(context, it, pendingIntent)
                    notificationManager?.notify(UNLOCK_NOTIFICATION_ID, notification)
                }
            }
        }
    }

    private fun getNotificationPendingIntent(context: Context?, studentId: Long): PendingIntent {
        // Create intent to launch main activity on click of notification
        val contentIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(KEY_LAUNCH_STUDENT_LIST, true)
            putExtra(KEY_STUDENT_ID, studentId)
            // Flags to clear notifications
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        }
        // Create pending intent that should be fired with notification
        return PendingIntent.getActivity(context, UNLOCK_NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun createNotification(
        context: Context?,
        student: Student,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(context!!, UNLOCK_PRIMARY_ID)
            .setSmallIcon(R.drawable.ic_stand_up).setContentTitle("Unlock alert!")
            .setContentText("${student.firstName} ${student.lastName} is unlocked!")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL).build()
    }


    companion object {
        const val UNLOCK_NOTIFICATION_ID = 12
        const val UNLOCK_PRIMARY_ID = "unlock_primary_channel_id"
        private const val UNLOCK_CHANNEL_NAME = "Unlock Channel"
        private const val UNLOCK_CHANNEL_DESCRIPTION = "A notification that tells your views are unlocked"
        const val KEY_LAUNCH_STUDENT_LIST = "openStudentList"
        const val KEY_STUDENT_ID = "selectedStudentKey"
    }
}