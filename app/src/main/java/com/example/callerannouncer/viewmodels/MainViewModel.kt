package com.example.callerannouncer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.callerannouncer.repository.AppRepository
import com.example.callerannouncer.room.model.AppModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(private val appRepository: AppRepository) : ViewModel() {
    var coroutine = CoroutineScope(Dispatchers.IO)
    var getAllAppList = MutableLiveData<List<AppModel>>()
    fun add(appModel: AppModel) {
        coroutine.launch {
            appRepository.addApp(appModel)
        }
    }

    fun updateApp(appID: Int, appPkgStatus: Boolean) {
        coroutine.launch {
            appRepository.updateAppStatus(appID, appPkgStatus)
        }
    }

    fun getAppPkgStatus(appPkgName: String, callback: (Boolean) -> Unit) {
        coroutine.launch {
            callback(appRepository.getAppPkgStatus(appPkgName))
        }

    }

    fun getAllApp(): LiveData<List<AppModel>> {
        getAllAppList = MutableLiveData()
        coroutine.launch {
            getAllAppList.postValue(appRepository.getAllApps())
        }
        return getAllAppList
    }


    override fun onCleared() {
        super.onCleared()
        coroutine.coroutineContext.cancel()
    }
}