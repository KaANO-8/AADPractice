package com.kaano8.androidsamples.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNotificationChannel()
        notify?.setOnClickListener {
            sendNotification()
        }
    }

    private fun setupNotificationChannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val mascotNotificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                MASCOT_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = "Notification from Mascot"
            }
            (activity?.getSystemService(NOTIFICATION_SERVICE) as? NotificationManager)?.createNotificationChannel(
                mascotNotificationChannel
            )
        }
    }

    private fun sendNotification() {
        activity?.let {
            val notificationBuilder = NotificationCompat.Builder(it, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_notif_name)
                .setContentIntent(PendingIntent.getActivity(it, NOTIFICATION_ID, Intent(it, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true)
            (activity?.getSystemService(NOTIFICATION_SERVICE) as? NotificationManager)?.notify(
                NOTIFICATION_ID, notificationBuilder.build()
            )
        }
    }

    companion object {
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        private const val MASCOT_NOTIFICATION_CHANNEL_NAME = "Mascot Notification Channel"
        private const val NOTIFICATION_ID = 0
    }
}
