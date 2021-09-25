package com.example.callerannouncer.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.callerannouncer.R
import com.example.callerannouncer.adsintegration.loadNativeAd
import com.example.callerannouncer.services.AnnouncerService
import com.example.callerannouncer.utils.Flash
import com.example.callerannouncer.utils.MyPreferences
import kotlinx.android.synthetic.main.calle_announce_permission_screen.*
import kotlinx.android.synthetic.main.calle_announce_permission_screen.cancelImageViewCallerID
import kotlinx.android.synthetic.main.calle_announce_permission_screen.notificationSwitchID
import kotlinx.android.synthetic.main.caller_flash_permission_screen.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.notification_flash_permission_screen.*
import org.koin.android.ext.android.inject

class HomeFragment : Fragment(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {
     val isForegroundService = "ForegroundService"
    private val MY_CAMERA_REQUEST_CODE = 100
    private val MY_READ_PHONE_STATE_REQUEST_CODE = 101
    lateinit var serviceIntent: Intent
    private val flash:Flash by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        requireContext().loadNativeAd(1,"abc",{},{},{})
    }

    private fun initialize() {
        serviceIntent = Intent(requireActivity(), AnnouncerService::class.java)
        serviceSwitchID.isChecked = MyPreferences.getBoolean(isForegroundService)
        callerLayoutID.setOnClickListener(this)
        smsLayoutID.setOnClickListener(this)
        callFlashLayoutID.setOnClickListener(this)
        notificationLayoutID.setOnClickListener(this)
        serviceSwitchID.setOnCheckedChangeListener(this)
    }

    private fun showPermissionScreen() {
        val permissionLayout =
            LayoutInflater.from(context)
                .inflate(R.layout.calle_announce_permission_screen, null, false)
        val view: AlertDialog = AlertDialog.Builder(requireContext()).create()
        view.setView(permissionLayout)
        view.setCancelable(false)
        view.show()
        view.notificationSwitchID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                notificationAccess()
            }
        }
        view.phoneCallSwitchID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                phoneStatesPermission()
            }
        }
        view.cancelImageViewCallerID.setOnClickListener {
            view.dismiss()
        }
    }

    private fun callerFlashPermission() {
        val permissionLayout =
            LayoutInflater.from(context)
                .inflate(R.layout.caller_flash_permission_screen, null, false)
        val callerFlashView: AlertDialog = AlertDialog.Builder(requireContext()).create()
        callerFlashView.setView(permissionLayout)
        callerFlashView.setCancelable(false)
        callerFlashView.show()
        callerFlashView.callerCameraSwitchID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cameraPermission()
            }
        }
        callerFlashView.readPhoneStateSwitchID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                phoneStatesPermission()
            }
        }
        callerFlashView.cancelImageViewCallerID.setOnClickListener {
            callerFlashView.dismiss()
        }
    }

    private fun notificationFlashPermission() {
        val permissionLayout =
            LayoutInflater.from(context)
                .inflate(R.layout.notification_flash_permission_screen, null, false)
        val flashPermissionView: AlertDialog = AlertDialog.Builder(requireContext()).create()
        flashPermissionView.setView(permissionLayout)
        flashPermissionView.setCancelable(false)
        flashPermissionView.show()
        flashPermissionView.cameraNotificationSwitchID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cameraPermission()
            }
        }
        flashPermissionView.notificationSwitchID.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                notificationAccess()
            }
        }
        flashPermissionView.cancelImageViewCallerID.setOnClickListener {
            flashPermissionView.dismiss()
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (isChecked) {
            startService()
        } else {
            stopService()
        }
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requireActivity().startForegroundService(serviceIntent)
        } else {
            requireActivity().startService(serviceIntent)
        }
        MyPreferences.putBoolean(isForegroundService, true)
        serviceSwitchID.isChecked = true
    }

    private fun stopService() {
        requireContext().stopService(serviceIntent)
        MyPreferences.putBoolean(isForegroundService, false)
        serviceSwitchID.isChecked = false
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.callerLayoutID -> showPermissionScreen()
            R.id.smsLayoutID -> showPermissionScreen()
            R.id.callFlashLayoutID -> {
                if (isPermissionGrated(Manifest.permission.CAMERA) && isPermissionGrated(Manifest.permission.READ_PHONE_STATE)) {
                    val action = HomeFragmentDirections.actionHomeFragmentToFlashOnCallFragment()
                    findNavController().navigate(action)
                } else {
                    callerFlashPermission()
                }
            }
            R.id.notificationLayoutID -> notificationFlashPermission()
        }
    }

    private fun notificationAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nAccessIntent = Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            context?.startActivity(nAccessIntent)
        } else {
            val nAccessIntent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            context?.startActivity(nAccessIntent)
        }
    }

    private fun phoneStatesPermission() {
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.READ_PHONE_STATE),
            MY_READ_PHONE_STATE_REQUEST_CODE
        )
    }

    private fun cameraPermission() {
        val activity = context as Activity
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            MY_CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_CAMERA_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toastMessage("Camera Permission Granted.")
                } else {
                    toastMessage("Camera Permission Denied.")
                }
            }
            MY_READ_PHONE_STATE_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    toastMessage("Read Phone State Permission Granted.")
                } else {
                    toastMessage("Read Phone State Permission Denied.")
                }
            }
        }
    }

    private fun isPermissionGrated(permission: String): Boolean {
        var value = -1
        value = ContextCompat.checkSelfPermission(requireContext(), permission)
        if (value == -1) {
            return false
        }
        return true
    }

    private fun toastMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
