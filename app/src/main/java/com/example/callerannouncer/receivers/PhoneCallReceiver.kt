package com.example.callerannouncer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log


class PhoneCallReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val state = intent!!.getStringExtra(TelephonyManager.EXTRA_STATE)
        when (state) {
            TelephonyManager.EXTRA_STATE_IDLE -> {
                Log.e("-123-", "rejected call")

            }
            TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                Log.e("-123-", "attend call")

            }
            TelephonyManager.EXTRA_STATE_RINGING -> {
                Log.e("-123-", "call ringing")
                val incomingNumber =
                    intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER) // 5


            }
        }

    }
}