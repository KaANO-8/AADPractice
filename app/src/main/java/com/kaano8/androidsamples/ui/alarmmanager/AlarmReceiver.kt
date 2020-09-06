package com.kaano8.androidsamples.ui.alarmmanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        deliverNotification(context)
    }

    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, AlarmManagerFragment.NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(context, AlarmManagerFragment.PRIMARY_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stand_up).setContentTitle("Stand Up Alert")
            .setContentText("You should stand up and walk around now!")
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        (context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.notify(AlarmManagerFragment.NOTIFICATION_ID, notificationBuilder.build())
    }
}
