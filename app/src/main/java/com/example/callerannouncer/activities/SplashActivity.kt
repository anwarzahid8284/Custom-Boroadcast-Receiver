package com.example.callerannouncer.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.callerannouncer.R
import com.example.callerannouncer.utils.Constant.Companion.SPLASH_DELAY_TIME

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        runnable= Runnable {
            run {
                val intentToHome = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(intentToHome)
                finish()
            }
        }
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed(runnable, SPLASH_DELAY_TIME)
    }

    override fun onRestart() {
        super.onRestart()
        Log.e("111","onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.e("111","onResume")

    }

    override fun onStart() {
        super.onStart()
        Log.e("111","onStart")

    }

    override fun onPause() {
        super.onPause()
        Log.e("111","onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("111","onDestroy")
        handler.removeCallbacks(runnable)
    }
}