package com.kaano8.androidsamples.ui.backgroundservices.jobscheduler

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.createChannel


class NotificationJobService : JobService() {

    private var notificationManager: NotificationManager? = null

    override fun onStartJob(params: JobParameters?): Boolean {

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as? NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationManager?.createChannel(
                JOB_SCHEDULER_NOTIFICATION_CHANNEL_ID,
                JOB_SCHEDULER_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
                JOB_SCHEDULER_NOTIFICATION_CHANNEL_DESCRIPTION
            )
        }

        val pendingIntent = PendingIntent.getActivity(
            baseContext, JOB_SCHEDULER_NOTIFICATION_ID, Intent(
                this,
                MainActivity::class.java
            ), PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(this, JOB_SCHEDULER_NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Job Service")
            .setContentText("Your Job ran to completion!")
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_job_running)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        notificationManager?.notify(0, builder.build())
        return false
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }


    companion object {
        internal const val JOB_SCHEDULER_NOTIFICATION_CHANNEL_ID = "JobSchedulerNotificationChannel"
        internal const val JOB_SCHEDULER_NOTIFICATION_CHANNEL_NAME = "Job Service notification"
        internal const val JOB_SCHEDULER_NOTIFICATION_CHANNEL_DESCRIPTION = "Notifications from Job Service"
        internal const val JOB_SCHEDULER_NOTIFICATION_ID = 1
    }
}