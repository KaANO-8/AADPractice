package com.kaano8.androidsamples.ui.studentmanagement.add

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.database.AppDatabase
import com.kaano8.androidsamples.database.student.Student
import com.kaano8.androidsamples.ui.backgroundservices.alarmmanager.AlarmManagerFragment
import com.kaano8.androidsamples.ui.studentmanagement.add.AddStudentFragment.Companion.STUDENT_ID_KEY
import com.kaano8.androidsamples.util.Injector.getNotificationManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UnlockAlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val notificationManager = getNotificationManager(
            context,
            UNLOCK_PRIMARY_ID,
            UNLOCK_CHANNEL_NAME,
            UNLOCK_CHANNEL_DESCRIPTION
        )
        val database = AppDatabase.getInstance(context?.applicationContext!!)
        val data = intent?.getLongExtra(STUDENT_ID_KEY, -1) ?: -1

        val contentIntent = Intent(context, MainActivity::class.java).apply {
            putExtra(KEY_LAUNCH_STUDENT_LIST, true)
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            AlarmManagerFragment.NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        GlobalScope.launch {
            val student = database.studentDao.getStudent(data)

            student?.let {
                val notification = createNotification(context, it, pendingIntent)
                notificationManager?.notify(UNLOCK_NOTIFICATION_ID, notification)
            }
        }
    }

    private fun createNotification(
        context: Context?,
        student: Student,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(context!!, AlarmManagerFragment.PRIMARY_CHANNEL_ID)
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
    }
}