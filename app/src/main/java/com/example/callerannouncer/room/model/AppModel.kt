package com.example.callerannouncer.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apps_data")
data class AppModel(
    @ColumnInfo(name = "appName")
    var name:String,

    @ColumnInfo(name="appPkgName")
    var pkgName:String,

    @ColumnInfo(name = "appStatus")
    var status:Boolean
    ) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="appID")
    var id:Int=0
}