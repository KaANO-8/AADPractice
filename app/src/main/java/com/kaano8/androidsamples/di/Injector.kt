package com.kaano8.androidsamples.di

import android.app.NotificationManager
import android.content.Context
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