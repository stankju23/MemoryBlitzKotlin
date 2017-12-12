package com.example.stanislavcavajda.memoryblitz

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_start.*
import android.view.animation.AlphaAnimation
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import kotlinx.android.synthetic.main.two_x_two.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.util.*


class StartActivity : AppCompatActivity() {

    val buttonClick = AlphaAnimation(1f, 0.8f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen()
        setContentView(R.layout.activity_start)

        DataManager.translatedGraphicPacks.add(getString(R.string.christmas_GP))
        DataManager.translatedGraphicPacks.add(getString(R.string.family_GP))
        DataManager.translatedGraphicPacks.add(getString(R.string.scenery_GP))
        DataManager.translatedGraphicPacks.add(getString(R.string.space_GP))
        DataManager.translatedGraphicPacks.add(getString(R.string.summer_GP))
        DataManager.translatedGraphicPacks.add(getString(R.string.toys_GP))
        DataManager.translatedGraphicPacks.add(getString(R.string.western_GP))

        start_game_type_menu.setOnClickListener {
            it.startAnimation(buttonClick)
            var gameTypeActivity = Intent(this,GameTypeActivity::class.java)
            startActivity(gameTypeActivity)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        OverScrollDecoratorHelper.setUpStaticOverScroll(main_layout,OverScrollDecoratorHelper.ORIENTATION_VERTICAL)

        score_button.setOnClickListener {
            it.startAnimation(buttonClick)
            var intent = Intent(this,HighScoreActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        settings_button.setOnClickListener {
            it.startAnimation(buttonClick)
            var intent = Intent(this,SettingsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        var preferences = this.getSharedPreferences("highScore",Context.MODE_PRIVATE)
        var gamePreferences = getSharedPreferences("game", Context.MODE_PRIVATE)
        DataManager.progressGameHighScore = preferences.getInt(Constants.PROGRESS_GAME_HIGH_SCORE,0)
        DataManager.classicGameHighScore = preferences.getInt(Constants.CLASSIC_GAME_HIGH_SCORE,0)
        DataManager.actualCheckedGraphicPack = gamePreferences.getString(Constants.GRAPHIC_PACK,"christmas")
        DataManager.cardMatrix = gamePreferences.getInt(Constants.CARD_MATRIX, 0)
        DataManager.numberOfWantedCards = gamePreferences.getInt(Constants.NUMBER_OF_WANTED_CARDS,0)
        DataManager.timeToMemorize = gamePreferences.getInt(Constants.TIME_TO_MEMORIZE,0)
        DataManager.numberOfCards = gamePreferences.getInt(Constants.NUMBER_OF_CARDS,0)

        Log.i("progres",DataManager.progressGameHighScore.toString())
        Log.i("clasic",DataManager.classicGameHighScore.toString())

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
