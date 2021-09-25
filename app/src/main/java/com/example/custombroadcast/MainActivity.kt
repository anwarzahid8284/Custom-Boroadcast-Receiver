package com.example.custombroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity() {

    lateinit var messsageText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messsageText = findViewById(R.id.getReceiverTextID)

        EventBus.getDefault().register(this)


    }

    override fun onStart() {
        super.onStart()
        val intenFilter = IntentFilter("com.example.custombroadcast.ACTION_SEND")
        registerReceiver(broadcastReceiver, intenFilter)

        val intentServiceFilter = IntentFilter(MyIntentService.MY_SERVICE_INTENT)
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(broadcastReceiverIntent, intentServiceFilter)
    }


    val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if ("com.example.custombroadcast.ACTION_SEND" == intent?.action) {
                val getMessage = intent.getStringExtra("message")
                messsageText.text = getMessage
            }
        }

    }

    val broadcastReceiverIntent = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message=intent?.getStringExtra("Key")
            messsageText.text=message
        }

    }



    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
        LocalBroadcastManager.getInstance(applicationContext)
            .unregisterReceiver(broadcastReceiverIntent)

    }

    fun SendBroadCast(view: View) {
        val intent = Intent("com.example.custombroadcast.ACTION_SEND")
        intent.putExtra("message", "Hia here i am custom broacast received.")
        sendBroadcast(intent)
    }

    fun explicitBCast(view: View) {
        val intent = Intent(this, ExplicitBroadCast::class.java)
        sendBroadcast(intent)
    }

    fun localbroadcast(view: View) {
        val intent = Intent(this,MyIntentService::class.java)
        intent.putExtra("key", "initial value.")
        startService(intent)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getDataFromEventBus(event:EventBusData){
        messsageText.text=event.message
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}