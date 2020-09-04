package com.kaano8.androidsamples.ui.alarmmanager

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.kaano8.androidsamples.MainActivity
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.createChannel
import com.kaano8.androidsamples.util.extensions.showToast
import kotlinx.android.synthetic.main.fragment_alarm_manager.*


class AlarmManagerFragment : Fragment() {

    private var notificationManager: NotificationManager? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alarm_manager, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notificationManager = activity?.getSystemService(NOTIFICATION_SERVICE) as? NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationManager?.createChannel(
                PRIMARY_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH,
                NOTIFICATION_CHANNEL_DESCRIPTION
            )
        }

        alarmManagerToggle?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                deliverNotification()
                activity?.showToast("Stand Up Alarm On!")
            } else {
                notificationManager?.cancelAll()
                activity?.showToast("Stand Up Alarm Off!")
            }
        }
    }

    private fun deliverNotification() {
        val contentIntent = Intent(activity?.baseContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(activity?.baseContext, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationBuilder = NotificationCompat.Builder(activity?.baseContext as Context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up).setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager?.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    companion object {

        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        private const val NOTIFICATION_ID = 0
        private const val NOTIFICATION_CHANNEL_NAME = "Stand up notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION =
            "Notifies every 15 minutes to stand up and walk"

        fun newInstance(param1: String, param2: String) = AlarmManagerFragment()
    }
}