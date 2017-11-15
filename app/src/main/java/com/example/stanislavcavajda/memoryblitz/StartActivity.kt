package com.example.stanislavcavajda.memoryblitz

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_start.*
import android.view.animation.AlphaAnimation
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*


class StartActivity : AppCompatActivity() {

    val buttonClick = AlphaAnimation(1f, 0.8f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen()
        setContentView(R.layout.activity_start)

        start_game_type_menu.setOnClickListener {
            it.startAnimation(buttonClick)
            var gameTypeActivity = Intent(this,GameTypeActivity::class.java)
            startActivity(gameTypeActivity)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        OverScrollDecoratorHelper.setUpStaticOverScroll(main_layout,OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        score_button.setOnClickListener {

        }

        settings_button.setOnClickListener {
            it.startAnimation(buttonClick)
            var intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
        }





    }

    override fun onRestart() {
        super.onRestart()
        setFullScreen()
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

    override fun onBackPressed() {
    }

}
