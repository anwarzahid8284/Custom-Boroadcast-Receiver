package com.example.callerannouncer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.telephony.TelephonyManager
import android.util.Log
import com.example.callerannouncer.utils.Flash
import com.example.callerannouncer.utils.MyPreferences
import com.example.callerannouncer.utils.MyPreferences.FLASH_TYPE_KEY
import org.koin.java.KoinJavaComponent.inject

class IncomingCallReceiver : BroadcastReceiver() {
    private val flash: Flash by inject(Flash::class.java)
    lateinit var audioManager: AudioManager
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            val telephonyManager = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            flash.initCamera(context!!)
            when (telephonyManager) {
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    Log.e("-123-", "CALL_STATE_IDLE")
                    flash.isBlinking = false
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    Log.e("-123-", "CALL_STATE_OFFHOOK")
                    flash.isBlinking = false
                }
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    Log.e("-123-", "CALL_STATE_RINGING")
                    if (MyPreferences.getBoolean(MyPreferences.INCOMING_CALL_KEY)) {
                        if (MyPreferences.getBoolean(MyPreferences.DONOT_BLINK_KEY)) {
                            if (!ScreenStatusReceiver.isScreenON) {
                                flash.isBlinking = true
                                phoneStatus(context)
                            }
                        } else {
                            flash.isBlinking = true
                            phoneStatus(context)
                        }
                    }
                }
            }
        }
    }

    private fun phoneStatus(context: Context?) {
        audioManager = context!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        when (audioManager.ringerMode) {
            AudioManager.RINGER_MODE_NORMAL -> {
                if (MyPreferences.getBoolean(MyPreferences.RING_MODE_KEY)) {
                    blinking()

                }
            }
            AudioManager.RINGER_MODE_VIBRATE -> {
                if (MyPreferences.getBoolean(MyPreferences.VIBRATION_MODE_KEY)) {
                    blinking()

                }
            }
            AudioManager.RINGER_MODE_SILENT -> {
                if (MyPreferences.getBoolean(MyPreferences.SILENT_MODE_KEY)) {
                    blinking()
                }
            }
        }

    }

    fun blinking() {
        if (MyPreferences.getString(FLASH_TYPE_KEY) == "threshold") {
            flash.frequencyBlinking()
        } else {
            flash.startBlinking()
        }
    }
}
