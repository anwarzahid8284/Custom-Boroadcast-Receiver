package com.example.callerannouncer.utils

import android.content.Context
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import android.os.Build
import com.example.callerannouncer.utils.MyPreferences.FLASH_NUMBER_TIMES
import com.example.callerannouncer.utils.MyPreferences.TIME_DEALAY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Flash {
    private lateinit var mCameraManager: CameraManager
    private lateinit var camera: Camera
    private lateinit var mCameraId: String
    var isBlinking = false

    fun initCamera(context: Context) {
        mCameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    }

    fun startBlinking() {
        var flashStatus = true
        CoroutineScope(Dispatchers.IO).launch {
            while (isBlinking) {
                flashStatus = if (flashStatus) {
                    flashON()
                    false
                } else {
                    flashOFF()
                    true
                }
                delay(MyPreferences.getLong(TIME_DEALAY))
            }
            if (!isBlinking) {
                flashOFF()
            }
        }

    }

    fun frequencyBlinking() {
        var flashStatus = true
        CoroutineScope(Dispatchers.IO).launch {
            val thresholds = MyPreferences.getInt(FLASH_NUMBER_TIMES)
            for (i in 1..thresholds) {
                if (isBlinking) {
                    flashStatus = if (flashStatus) {
                        flashON()
                        false
                    } else {
                        flashOFF()
                        true
                    }
                    delay(MyPreferences.getLong(TIME_DEALAY))
                }
            }
            if (!isBlinking) {
                flashOFF()
            }
        }

    }

    private fun flashON() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                mCameraId = mCameraManager.cameraIdList[0]
                mCameraManager.setTorchMode(mCameraId, true)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            camera = Camera.open()
            val parameters: Camera.Parameters = camera.parameters
            parameters.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera.parameters = parameters
            camera.startPreview()
        }
    }

    private fun flashOFF() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mCameraId = mCameraManager.cameraIdList[0]
            mCameraManager.setTorchMode(mCameraId, false)
        } else {
            camera.stopPreview()
            camera.release()
        }
    }
}
