package com.kaano8.androidsamples.util

import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import com.kaano8.androidsamples.ui.backgroundservices.alarmmanager.AlarmManagerFragment
import com.kaano8.androidsamples.util.extensions.createChannel

object Injector {

    fun getNotificationManager(
        context: Context?,
        primaryChannelId: String,
        channelName: String,
        channelDescription: String
    ): NotificationManager? =
        (context?.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager)?.also {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                it.createChannel(
                    primaryChannelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH,
                    channelDescription
                )
            }
        }

}