package com.example.stanislavcavajda.memoryblitz

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.google.android.gms.ads.MobileAds


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var preferences = getSharedPreferences(Constants.NOTIFICATION_TIME, Context.MODE_PRIVATE)
        DataManager.notificationsTimeHour = preferences.getInt(Constants.NOTIFICATION_HOURS,0)
        DataManager.notificationsTimeMinute = preferences.getInt(Constants.NOTIFICATION_MINUTES,0)
        var handler = Handler()
        handler.postDelayed(Runnable {
            var activity = Intent(this,StartActivity::class.java)
            startActivity(activity)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        },1000)

        //MobileAds.initialize(this,"ca-app-pub-7144286645481402/8843518771")
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713") //test

    }


    fun setFullScreen() {
        val decor_View = window.decorView

        val ui_Options = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        decor_View.systemUiVisibility = ui_Options
    }
}
