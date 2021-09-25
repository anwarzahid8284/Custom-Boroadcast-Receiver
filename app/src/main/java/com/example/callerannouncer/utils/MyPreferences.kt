package com.example.callerannouncer.utils

import android.content.Context
import android.content.SharedPreferences

object MyPreferences {
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    val INCOMING_CALL_KEY = "incomingCallKey"
    val DONOT_BLINK_KEY = "doNotBlinkKey"
    val RING_MODE_KEY = "ringModeKey"
    val VIBRATION_MODE_KEY = "vibrationModeKey"
    val SILENT_MODE_KEY = "silentModeKey"
    val FLASH_TYPE_KEY = "flashtypekey"
    val TIME_DEALAY = "blinkingDelay"
    val SEEKBAR_PROGRESS = "progress"
    val FLASH_NUMBER_TIMES="flashNumberTimes"
    fun initPreference(context: Context) {
        sharedPreferences = context.getSharedPreferences("Preference", Context.MODE_PRIVATE)

    }

    fun putBoolean(key: String, value: Boolean) {
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putBoolean(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    fun putInt(key: String, value: Int) {
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putInt(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 10)
    }

    fun putString(key: String, value: String) {
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putString(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun putLong(key: String, value: Long) {
        sharedPreferencesEditor = sharedPreferences.edit()
        sharedPreferencesEditor.putLong(key, value)
        sharedPreferencesEditor.apply()
    }

    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 50)
    }
}
