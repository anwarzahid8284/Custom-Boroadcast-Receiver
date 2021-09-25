package com.example.callerannouncer.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.example.callerannouncer.fragments.HomeFragment
import com.example.callerannouncer.services.AnnouncerService
import com.example.callerannouncer.utils.MyPreferences
import org.koin.java.KoinJavaComponent.inject

class BootReceiver : BroadcastReceiver() {
    private val homeFragment: HomeFragment by inject(HomeFragment::class.java)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent!!.action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Toast.makeText(context, "Called", Toast.LENGTH_SHORT).show()
            if (MyPreferences.getBoolean(homeFragment.isForegroundService)) {
                val serviceIntent = Intent(context, AnnouncerService::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context!!.startForegroundService(serviceIntent)
                } else {
                    context!!.startService(serviceIntent)
                }
            }
        }
    }
}