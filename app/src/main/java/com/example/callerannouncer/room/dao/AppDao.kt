package com.example.callerannouncer.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.callerannouncer.room.model.AppModel

@Dao
interface AppDao {
    @Insert
    suspend fun addApp(appModel: AppModel)

    @Query("update apps_data set appStatus=:pkgStatus where appPkgName=:pkgName")
    suspend fun updateApp(pkgName: Int, pkgStatus: Boolean)

    @Query("select appStatus from apps_data where appPkgName=:appPkgName")
    suspend fun getPkgStatus(appPkgName: String): Boolean

    @Query("select * from apps_data")
    suspend fun getAllApps(): List<AppModel>
}