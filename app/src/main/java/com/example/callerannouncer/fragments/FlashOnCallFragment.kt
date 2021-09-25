package com.example.callerannouncer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.callerannouncer.R
import com.example.callerannouncer.utils.MyPreferences
import com.example.callerannouncer.utils.MyPreferences.DONOT_BLINK_KEY
import com.example.callerannouncer.utils.MyPreferences.FLASH_NUMBER_TIMES
import com.example.callerannouncer.utils.MyPreferences.FLASH_TYPE_KEY
import com.example.callerannouncer.utils.MyPreferences.INCOMING_CALL_KEY
import com.example.callerannouncer.utils.MyPreferences.RING_MODE_KEY
import com.example.callerannouncer.utils.MyPreferences.SEEKBAR_PROGRESS
import com.example.callerannouncer.utils.MyPreferences.SILENT_MODE_KEY
import com.example.callerannouncer.utils.MyPreferences.TIME_DEALAY
import com.example.callerannouncer.utils.MyPreferences.VIBRATION_MODE_KEY
import kotlinx.android.synthetic.main.fragment_flash_on_call.*

class FlashOnCallFragment : Fragment(), CompoundButton.OnCheckedChangeListener,
    SeekBar.OnSeekBarChangeListener {

    var delaySpeedProgressValue= 1
    var flashNumberOfTimes= 1
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_flash_on_call, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        speedBlinkingSeekBarID.setOnSeekBarChangeListener(this)
        frequencySeekBarID.setOnSeekBarChangeListener(this)
        radioGrpID.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.blinkingRadioButtonID -> {
                    enableBlinkingMode()
                }
                R.id.frequencyRadioButtonID -> {
                    enableThresholdMode()
                }
            }
        }

        initRes()

    }

    private fun initRes() {
        incomingCallSwitchID.setOnCheckedChangeListener(this)
        doNotBlinkSwitchID.setOnCheckedChangeListener(this)
        ringModeSwitchID.setOnCheckedChangeListener(this)
        vibrationModeSwitchID.setOnCheckedChangeListener(this)
        silentModeSwitchID.setOnCheckedChangeListener(this)
        if (MyPreferences.getString(FLASH_TYPE_KEY)
                .equals("") || MyPreferences.getString(FLASH_TYPE_KEY)
                .equals("continue")
        ) {
            enableBlinkingMode()
        } else {
            enableThresholdMode()

        }
        if (MyPreferences.getBoolean(INCOMING_CALL_KEY)) {
            visibilityON()
            speedBlinkingSeekBarID.progress = MyPreferences.getInt(SEEKBAR_PROGRESS)
        } else {
            visibilityOFF()
        }
        incomingCallSwitchID.isChecked = MyPreferences.getBoolean(INCOMING_CALL_KEY)
        doNotBlinkSwitchID.isChecked = MyPreferences.getBoolean(DONOT_BLINK_KEY)
        ringModeSwitchID.isChecked = MyPreferences.getBoolean(RING_MODE_KEY)
        vibrationModeSwitchID.isChecked = MyPreferences.getBoolean(VIBRATION_MODE_KEY)
        silentModeSwitchID.isChecked = MyPreferences.getBoolean(SILENT_MODE_KEY)
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView!!.id) {
            R.id.incomingCallSwitchID -> {
                if (isChecked) {
                    MyPreferences.putBoolean(INCOMING_CALL_KEY, isChecked)
                    visibilityON()
                } else {
                    MyPreferences.putBoolean(INCOMING_CALL_KEY, isChecked)
                    visibilityOFF()
                }

            }
            R.id.doNotBlinkSwitchID -> {
                if (isChecked) {
                    MyPreferences.putBoolean(DONOT_BLINK_KEY, isChecked)

                } else {
                    MyPreferences.putBoolean(DONOT_BLINK_KEY, isChecked)
                }
            }
            R.id.ringModeSwitchID -> {
                if (isChecked) {
                    MyPreferences.putBoolean(RING_MODE_KEY, isChecked)

                } else {
                    MyPreferences.putBoolean(RING_MODE_KEY, isChecked)
                }
            }
            R.id.vibrationModeSwitchID -> {
                if (isChecked) {
                    MyPreferences.putBoolean(VIBRATION_MODE_KEY, isChecked)

                } else {
                    MyPreferences.putBoolean(VIBRATION_MODE_KEY, isChecked)
                }
            }
            R.id.silentModeSwitchID -> {
                if (isChecked) {
                    MyPreferences.putBoolean(SILENT_MODE_KEY, isChecked)

                } else {
                    MyPreferences.putBoolean(SILENT_MODE_KEY, isChecked)
                }

            }
        }
    }

    private fun visibilityON() {
        if (profileContainerID.isInvisible) {
            profileContainerID.visibility = View.VISIBLE
        }
        if (settingContainerID.isInvisible) {
            settingContainerID.visibility = View.VISIBLE
        }
    }

    private fun visibilityOFF() {
        if (profileContainerID.isVisible) {
            profileContainerID.visibility = View.INVISIBLE
        }
        if (settingContainerID.isVisible) {
            settingContainerID.visibility = View.INVISIBLE
        }
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

        when (seekBar!!.id) {

            R.id.speedBlinkingSeekBarID -> {
                delaySpeedProgressValue=progress
                if(delaySpeedProgressValue==0){
                    delaySpeedProgressValue=1
                }
                speedBlinkingSeekBarID.progress = delaySpeedProgressValue
                speedTimeTextID.text = "${delayTime(delaySpeedProgressValue)} ms"
            }
            R.id.frequencySeekBarID -> {
                flashNumberOfTimes=progress
                if(flashNumberOfTimes==0){
                    flashNumberOfTimes=1
                }
                frequencySeekBarID.progress = flashNumberOfTimes
                frequencyTextID.text = "$flashNumberOfTimes Times"
            }
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        when (seekBar!!.id) {
            R.id.speedBlinkingSeekBarID -> {
                delaySpeedProgressValue=seekBar.progress
                if(delaySpeedProgressValue==0){
                    delaySpeedProgressValue=1
                }
                MyPreferences.putInt(SEEKBAR_PROGRESS, delaySpeedProgressValue)
                MyPreferences.putLong(TIME_DEALAY, delayTime(delaySpeedProgressValue))
            }
            R.id.frequencySeekBarID -> {
                flashNumberOfTimes=seekBar.progress
                if(flashNumberOfTimes==0){
                    flashNumberOfTimes=1
                }
                MyPreferences.putInt(FLASH_NUMBER_TIMES, flashNumberOfTimes)
            }
        }
    }

    private fun delayTime( progressValue: Int): Long {
        return ((11 - progressValue) * 50).toLong()
    }

    private fun enableBlinkingMode() {
        Toast.makeText(context, "contines", Toast.LENGTH_SHORT).show()
        MyPreferences.putString(FLASH_TYPE_KEY, "continue")
        speedTimeTextID.text="${MyPreferences.getLong(TIME_DEALAY)} ms"
        speedBlinkingSeekBarID.progress=MyPreferences.getInt(SEEKBAR_PROGRESS)
        blinkingRadioButtonID.isChecked = true
        speedBlinkingSeekBarID.isEnabled = true
        frequencySeekBarID.isEnabled = false
        speedBlinkingSeekBarID.progressDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.seekbar_style, null)
        frequencySeekBarID.progressDrawable =
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.disable_seekbar_style,
                null
            )
        speedBlinkingSeekBarID.thumb =
            ResourcesCompat.getDrawable(resources, R.drawable.custom_thum, null)
        frequencySeekBarID.thumb =
            ResourcesCompat.getDrawable(resources, R.drawable.disable_thumb, null)


    }

    private fun enableThresholdMode() {
        Toast.makeText(context, "frequency", Toast.LENGTH_SHORT).show()
        MyPreferences.putString(FLASH_TYPE_KEY, "threshold")
        frequencySeekBarID.progress=MyPreferences.getInt(FLASH_NUMBER_TIMES)
        frequencyTextID.text = "${MyPreferences.getInt(FLASH_NUMBER_TIMES)} Times"
        frequencyRadioButtonID.isChecked = true
        speedBlinkingSeekBarID.isEnabled = false
        frequencySeekBarID.isEnabled = true
        speedBlinkingSeekBarID.progressDrawable =
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.disable_seekbar_style,
                null
            )
        frequencySeekBarID.progressDrawable =
            ResourcesCompat.getDrawable(resources, R.drawable.seekbar_style, null)
        speedBlinkingSeekBarID.thumb =
            ResourcesCompat.getDrawable(resources, R.drawable.disable_thumb, null)
        frequencySeekBarID.thumb =
            ResourcesCompat.getDrawable(resources, R.drawable.custom_thum, null)
    }

}
