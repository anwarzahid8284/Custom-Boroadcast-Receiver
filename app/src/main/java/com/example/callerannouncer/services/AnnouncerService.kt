package com.example.callerannouncer.services

import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.callerannouncer.R
import com.example.callerannouncer.activities.HomeActivity
import com.example.callerannouncer.receivers.IncomingCallReceiver
import com.example.callerannouncer.receivers.ScreenStatusReceiver


class AnnouncerService : Service() {
    val CHANNEL_ID = "ForegroundServiceChannel"
    private lateinit var screenStatusReceiver: ScreenStatusReceiver
    private lateinit var incomingCallReceiver: IncomingCallReceiver

    override fun onCreate() {
        super.onCreate()
        screenStatusReceiver = ScreenStatusReceiver()
        incomingCallReceiver = IncomingCallReceiver()
        val receiverFilter = IntentFilter()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        receiverFilter.addAction("android.intent.action.PHONE_STATE")
        registerReceiver(incomingCallReceiver, receiverFilter)
        registerReceiver(screenStatusReceiver, intentFilter)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
            val notificationIntent = Intent(this, HomeActivity::class.java)
            val contentView = RemoteViews(packageName, R.layout.custom_notification)

            val pendingIntent = PendingIntent.getActivity(
                this,
                0, notificationIntent, 0
            )
            val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_phone_call)
                .setContent(contentView)
                .setContentIntent(pendingIntent)
                .build()
            startForeground(1, notification)
        } else {
            startForeground(1, Notification())
        }

        return START_NOT_STICKY
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        stopSelf()
        stopForeground(true)
        unregisterReceiver(incomingCallReceiver)
        unregisterReceiver(screenStatusReceiver)
    }

}
