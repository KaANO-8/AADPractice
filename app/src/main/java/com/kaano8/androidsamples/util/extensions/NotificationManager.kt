package com.kaano8.androidsamples.util.extensions

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color

fun NotificationManager.createChannel(primaryChannelId: String, channelName: String, importance: Int, description: String = "") {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        createNotificationChannel(NotificationChannel(primaryChannelId, channelName, importance).apply {
            enableLights(true)
            enableVibration(true)
            lightColor = Color.RED
            setDescription(description)
        })
    }
}