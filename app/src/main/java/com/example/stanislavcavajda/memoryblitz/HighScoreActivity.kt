package com.example.stanislavcavajda.memoryblitz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_high_score.*
import com.example.stanislavcavajda.memoryblitz.Data.DataManager

class HighScoreActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_high_score)

        setFullScreen()

        progress_game_score.setText(DataManager.progressGameHighScore.toString())
        classic_game_score.setText(DataManager.classicGameHighScore.toString())
        back_btn.setOnClickListener {

            this.finish()
            super.onBackPressed()
            overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom)
        }
    }

    override fun onBackPressed() {
        this.finish()
        super.onBackPressed()
        overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom)
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
