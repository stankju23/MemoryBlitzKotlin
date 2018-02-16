package com.example.stanislavcavajda.memoryblitz


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.aigestudio.wheelpicker.WheelPicker
import kotlinx.android.synthetic.main.activity_settings.*
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import java.util.*
import com.example.stanislavcavajda.memoryblitz.Data.Constants

class SettingsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)




        //notification intent and alarm manager
        var intent = Intent(applicationContext,NotificationReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(applicationContext,100,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        var alarmManager = (getSystemService(ALARM_SERVICE) as AlarmManager)

        //create alert dialog
        var alertDialogBuilder = AlertDialog.Builder(this,android.R.style.Theme_Material_Light_Dialog_Alert)
        alertDialogBuilder.setTitle(R.string.notification)
        alertDialogBuilder.setIcon(R.drawable.notification_icon)

        // inflate custom layout to dialog
        var inflater = this.layoutInflater
        var dialogView = inflater.inflate(R.layout.notification_dialog,null)
        var wheelPickerHours = dialogView.findViewById<WheelPicker>(R.id.wheelpicker_hours)
        var wheelPickerMinutes = dialogView.findViewById<WheelPicker>(R.id.wheelpicker_minutes)


        // reading from shared preferences
        var preferences = this.getPreferences(Context.MODE_PRIVATE)
        DataManager.notificationsOn = preferences.getBoolean(Constants.NOTIFICATION_STATUS,false)

        // getting current selected position
        wheelPickerHours.setOnItemSelectedListener( WheelPicker.OnItemSelectedListener { picker, data, position ->
            DataManager.notificationsTimeHour = position

        })
        wheelPickerMinutes.setOnItemSelectedListener( WheelPicker.OnItemSelectedListener { picker, data, position ->
            DataManager.notificationsTimeMinute = position
        })

        // add positive button to dialog
        alertDialogBuilder.setPositiveButton(R.string.yes, DialogInterface.OnClickListener { dialogInterface, i ->

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, DataManager.notificationsTimeHour)
            calendar.set(Calendar.MINUTE,DataManager.notificationsTimeMinute)
            calendar.set(Calendar.SECOND,0)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent)
            saveNotificationTime(DataManager.notificationsTimeHour,DataManager.notificationsTimeMinute)
        })

        alertDialogBuilder.setOnCancelListener {

            var calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, DataManager.notificationsTimeHour)
            calendar.set(Calendar.MINUTE,DataManager.notificationsTimeMinute)
            calendar.set(Calendar.SECOND,0)
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent)
            saveNotificationTime(DataManager.notificationsTimeHour,DataManager.notificationsTimeMinute)

        }

        // set custom view to dialog
        alertDialogBuilder.setView(dialogView)

        // preparing datas to wheel pickers
        var hours = ArrayList<String>()
        var minutes = ArrayList<String>()
        for (i in 0..59) {
            if (i <= 23) {
                hours.add("$i hour")
            }
            minutes.add("$i minute")
        }

        // add datas to wheel pickers
        wheelPickerHours.data = hours
        wheelPickerMinutes.data = minutes


        var alertDialog = alertDialogBuilder.create()


        about_btn.setOnClickListener {

            var intent = Intent(this,AboutActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        switch_btn.isChecked = DataManager.notificationsOn

        switch_btn.setOnCheckedChangeListener { compoundButton, check ->

            if (check) {
                DataManager.notificationsOn = true
                var editor = preferences.edit()
                editor.putBoolean(Constants.NOTIFICATION_STATUS,DataManager.notificationsOn)
                editor.commit()
                setWheelPickerActualPostion(wheelPickerHours,wheelPickerMinutes,DataManager.notificationsTimeHour,DataManager.notificationsTimeMinute)
                alertDialog.show()
            } else {
                DataManager.notificationsOn = false
                var editor = preferences.edit()
                editor.putBoolean(Constants.NOTIFICATION_STATUS,DataManager.notificationsOn)
                editor.commit()
                alarmManager.cancel(pendingIntent)
            }
        }



        notification_btn.setOnClickListener {

            setWheelPickerActualPostion(wheelPickerHours,wheelPickerMinutes,DataManager.notificationsTimeHour,DataManager.notificationsTimeMinute)
            if (DataManager.notificationsOn){
                alertDialog.show()
            }
        }
    }

    fun setWheelPickerActualPostion(wheelpicker1:WheelPicker , wheelpicker2:WheelPicker,pickerPositionHours: Int, pickerPositionMinutes: Int) {
        Log.i("Hours",pickerPositionHours.toString())
        Log.i("Minutes",pickerPositionMinutes.toString())
        wheelpicker1.selectedItemPosition = pickerPositionHours
        wheelpicker2.selectedItemPosition = pickerPositionMinutes
    }

    fun saveNotificationTime(hours: Int, minutes:Int) {
        var preferences = getSharedPreferences(Constants.NOTIFICATION_TIME, Context.MODE_PRIVATE)
        var editor = preferences.edit()
        editor.putInt(Constants.NOTIFICATION_HOURS, hours)
        editor.putInt(Constants.NOTIFICATION_MINUTES,minutes)
        editor.commit()
    }

    override fun onBackPressed() {
        this.finish()
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
    }
}


