package com.example.callerannouncer.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.callerannouncer.room.dao.AppDao
import com.example.callerannouncer.room.model.AppModel

@Database(entities = [AppModel::class],version = 1,exportSchema = false)
abstract class AppDB:RoomDatabase() {
    abstract fun appDao():AppDao
}