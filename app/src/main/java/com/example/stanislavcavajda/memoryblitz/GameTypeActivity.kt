package com.example.stanislavcavajda.memoryblitz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import kotlinx.android.synthetic.main.activity_game_type.*

class GameTypeActivity : AppCompatActivity() {

    val buttonClick = AlphaAnimation(1f, 0.8f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen()
        setContentView(R.layout.activity_game_type)

        speed_game.setOnClickListener {
            this.finish()
            DataManager.typeSettingsActivity = Constants.CLASSIC_GAME
            it.startAnimation(buttonClick)
            var intent = Intent(this, ClassicGameSettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        endless_game.setOnClickListener {
            this.finish()
            DataManager.typeSettingsActivity = Constants.PROGRESS_GAME
            it.startAnimation(buttonClick)
            var intent = Intent(this, ProgressiveGameSettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        back_button.setOnClickListener {
            super.onBackPressed()
            overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom)
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
