package com.kaano8.androidsamples.ui.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.kaano8.androidsamples.BuildConfig
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.showToast
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder
    private val notificationReceiver = NotificationReceiver()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.registerReceiver(notificationReceiver, IntentFilter(ACTION_UPDATE_NOTIFICATION))
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNotificationManger()
        setupNotificationChannel()
        setNotificationButtonState(true, false, false)
        // Click listener for notify button
        notify?.setOnClickListener { sendNotification() }
        // Click listener for update button
        update?.setOnClickListener { updateNotification() }
        // Click listener for cancel button
        cancel?.setOnClickListener { cancelButton() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        activity?.unregisterReceiver(notificationReceiver)
    }

    private fun initNotificationManger() {
        notificationManager = (activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
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
            notificationManager.createNotificationChannel(mascotNotificationChannel)
        }
    }

    private fun sendNotification() {

        activity?.let {
            val updatePendingIntent = PendingIntent.getBroadcast(activity, NOTIFICATION_ID, Intent(ACTION_UPDATE_NOTIFICATION), PendingIntent.FLAG_ONE_SHOT)
            notificationBuilder = NotificationCompat.Builder(it, PRIMARY_CHANNEL_ID)
                .setContentTitle("You've been notified!")
                .setContentText("This is your notification text.")
                .setSmallIcon(R.drawable.ic_notif_name)
                .setContentIntent(
                    PendingIntent.getActivity(
                        it, NOTIFICATION_ID, Intent(
                            it,
                            MainActivity::class.java
                        ), PendingIntent.FLAG_UPDATE_CURRENT
                    )
                )
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(R.drawable.ic_update_action, "Update Notification", updatePendingIntent)
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            setNotificationButtonState(false, true, true)
        }
    }

    fun updateNotification() {
        val androidImage = BitmapFactory.decodeResource(resources, R.drawable.mascot_1)
        if (::notificationBuilder.isInitialized) {
            notificationBuilder.setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(
                    androidImage
                ).setBigContentTitle("Notification Updated!")
            )
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
            setNotificationButtonState(false, false, true)
        } else {
            activity?.showToast("Notification builder is not yet init!")
        }
    }

    private fun cancelButton() {
        if (::notificationManager.isInitialized) {
            notificationManager.cancel(NOTIFICATION_ID)
            setNotificationButtonState(true, false, false)
        }
        else
            activity?.showToast("Notification manager not init yet!")
    }

    private fun setNotificationButtonState(isNotifyEnabled: Boolean, isUpdateEnabled: Boolean, isCancelEnabled: Boolean) {
        notify?.isEnabled = isNotifyEnabled
        update?.isEnabled = isUpdateEnabled
        cancel?.isEnabled = isCancelEnabled
    }

    companion object {
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        private const val MASCOT_NOTIFICATION_CHANNEL_NAME = "Mascot Notification Channel"
        private const val NOTIFICATION_ID = 0
        private const val ACTION_UPDATE_NOTIFICATION = BuildConfig.APPLICATION_ID + "ACTION_UPDATE_NOTIFICATION"
    }

    inner class NotificationReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNotification()
        }
    }
}
