package com.example.callerannouncer.repository

import com.example.callerannouncer.room.dao.AppDao
import com.example.callerannouncer.room.model.AppModel

class AppRepository(private val appDao: AppDao) {
    suspend fun addApp(appModel: AppModel)=appDao.addApp(appModel)

    suspend fun updateAppStatus(appID:Int,appPkgStatus:Boolean)=appDao.updateApp(appID,appPkgStatus)

    suspend fun getAppPkgStatus(appPkgName:String)=appDao.getPkgStatus(appPkgName)

    suspend fun getAllApps():List<AppModel> =appDao.getAllApps()
}