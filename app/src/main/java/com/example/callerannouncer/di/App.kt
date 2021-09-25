package com.example.callerannouncer.di

import android.app.Application
import com.example.callerannouncer.utils.MyPreferences
import com.google.android.gms.ads.MobileAds
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
            MyPreferences.initPreference(this@App)
        }
    }
}