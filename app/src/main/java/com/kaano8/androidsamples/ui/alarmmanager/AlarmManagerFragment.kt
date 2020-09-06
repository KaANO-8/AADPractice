package com.kaano8.androidsamples.ui.alarmmanager

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kaano8.androidsamples.R
import com.kaano8.androidsamples.util.extensions.createChannel
import com.kaano8.androidsamples.util.extensions.showToast
import kotlinx.android.synthetic.main.fragment_alarm_manager.*


class AlarmManagerFragment : Fragment() {

    private var notificationManager: NotificationManager? = null

    private var alarmManager: AlarmManager? = null

    private var notifyPendingIntent: PendingIntent? = null

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

        val alarmUp = PendingIntent.getBroadcast(activity?.baseContext, NOTIFICATION_ID, Intent(activity?.baseContext, AlarmReceiver::class.java), PendingIntent.FLAG_NO_CREATE) != null

        alarmManagerToggle?.isChecked = alarmUp

        alarmManagerToggle?.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setupAlarm()
            } else {
                cancelAlarm()
                notificationManager?.cancelAll()
                activity?.showToast("Stand Up Alarm Off!")
            }
        }
    }

    private fun setupAlarm() {
        notifyPendingIntent =PendingIntent.getBroadcast(activity?.baseContext, NOTIFICATION_ID, Intent(activity?.baseContext, AlarmReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager = activity?.baseContext?.getSystemService(ALARM_SERVICE) as? AlarmManager

        alarmManager?.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, notifyPendingIntent)
    }

    private fun cancelAlarm() {
        alarmManager?.cancel(notifyPendingIntent)
    }

    companion object {
        internal const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        internal const val NOTIFICATION_ID = 0
        private const val NOTIFICATION_CHANNEL_NAME = "Stand up notification"
        private const val NOTIFICATION_CHANNEL_DESCRIPTION =
            "Notifies every 15 minutes to stand up and walk"
    }
}