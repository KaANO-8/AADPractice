package com.kaano8.androidsamples.ui.backgroundservices.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.kaano8.androidsamples.util.extensions.showToast

class PowerStatusReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var message = "Unknown intent"
        when(intent.action) {
            Intent.ACTION_POWER_CONNECTED -> message = "Power connected"
            Intent.ACTION_POWER_DISCONNECTED -> message = "Power disconnected"
        }
        context.showToast(message)
    }
}
