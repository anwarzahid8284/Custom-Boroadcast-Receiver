package com.example.callerannouncer.services

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification

class NLService : NotificationListenerService() {
    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)
    }
}
