package com.example.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings

class AirplaneModeReceiver(
    val onEnabled: () -> Unit,
    val onDisabled: () -> Unit
): BroadcastReceiver() {

    private var registered = false

    override fun onReceive(context: Context?, p1: Intent?) {
        val bool = Settings.Global.getInt(context?.contentResolver, Settings.Global.AIRPLANE_MODE_ON) != 0
        if (bool) onEnabled() else onDisabled()
    }

    fun register(context: Context) {
        if (!registered) {
            val filter = IntentFilter()
            filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            context.registerReceiver(this, filter)
            registered = true
        }
    }

    fun unregister(context: Context) {
        if (registered) {
            context.unregisterReceiver(this)
            registered = false
        }
    }
}