package com.example.custombroadcast

import android.app.IntentService
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.greenrobot.eventbus.EventBus

class MyIntentService : IntentService("MyIntentSevice") {
   companion object{
       val MY_SERVICE_INTENT = "Service"
   }
    override fun onHandleIntent(intent: Intent?) {
        if(intent!=null){
            val message1=intent.getStringExtra("key")
            val eventBus= message1?.let { EventBusData(it) }
            EventBus.getDefault().post(eventBus)
            try {
                Thread.sleep(4000)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            val intentGet = Intent(MY_SERVICE_INTENT)
            intentGet.putExtra("key", intent)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intentGet)


        }

    }
}