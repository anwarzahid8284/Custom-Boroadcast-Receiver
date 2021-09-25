package com.example.callerannouncer.di

import androidx.room.Room
import com.example.callerannouncer.fragments.HomeFragment
import com.example.callerannouncer.repository.AppRepository
import com.example.callerannouncer.room.db.AppDB
import com.example.callerannouncer.utils.Constant.Companion.DB_NAME
import com.example.callerannouncer.utils.Flash
import com.example.callerannouncer.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDB::class.java, DB_NAME)
            .fallbackToDestructiveMigration().build()
    }
    single { get<AppDB>().appDao() }
    single { AppRepository(get()) }
    viewModel { MainViewModel(get()) }



    single { Flash() }
    single { HomeFragment() }
}