package com.example.callerannouncer.receivers

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.callerannouncer.utils.Flash
import org.koin.java.KoinJavaComponent.inject

class ScreenStatusReceiver : BroadcastReceiver() {
    private val flash: Flash by inject(Flash::class.java)

    companion object {
        var isScreenON = false
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action.equals(Intent.ACTION_SCREEN_OFF)) {
            isScreenON = false
            flash.isBlinking = false
            Log.e(TAG, "ScreenStatus Receiver-onReceive off")
        } else if (intent?.action.equals(Intent.ACTION_SCREEN_ON)) {
            isScreenON = true
            Log.e(TAG, "ScreenStatus Receiver-onReceive onn")
        }
    }
}
