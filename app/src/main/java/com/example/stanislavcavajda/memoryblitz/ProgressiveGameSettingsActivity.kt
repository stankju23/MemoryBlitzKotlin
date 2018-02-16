package com.example.stanislavcavajda.memoryblitz

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardViewModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardListViewModel
import com.example.stanislavcavajda.memoryblitz.databinding.ActivityEndlessGameSettingsBinding
import kotlinx.android.synthetic.main.activity_endless_game_settings.*
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import kotlin.concurrent.thread


class ProgressiveGameSettingsActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()


        var preferences = getSharedPreferences("game", Context.MODE_PRIVATE)

        var binding: ActivityEndlessGameSettingsBinding = DataBindingUtil.setContentView(this,R.layout.activity_endless_game_settings)

        DataManager.graphicPacks.clear()

        var index = 0;
        for (item in DataManager.graphicPacksNames) {
            if (item == DataManager.actualCheckedGraphicPack) {
                DataManager.graphicPacks.add(CardViewModel("${item}_1", this, true, DataManager.translatedGraphicPacks.get(index)))
            } else {
                DataManager.graphicPacks.add(CardViewModel("${item}_1", this, false, DataManager.translatedGraphicPacks.get(index)))
            }
            index++;
        }

        var viewModel = CardListViewModel(DataManager.graphicPacks,this)
        binding.viewModel = viewModel

        var cards = ArrayList<String>()
        for(i in 3..9) {
            var cardString = resources.getString(R.string.card)
            if (cardString != "karta" && i < 5 && cardString != "cards" && cardString != "card")  {
                cardString = "karty"
            }
            cards.add(i-3,"$i ${cardString}")
        }
        cards_number_picker.data = cards
        cards_number_picker.selectedItemPosition = DataManager.numberOfCards



        button2.setOnClickListener {
            var editor = preferences.edit()
            editor.putString(Constants.GRAPHIC_PACK, DataManager.actualCheckedGraphicPack)
            editor.putInt(Constants.NUMBER_OF_CARDS, DataManager.numberOfCards)
            editor.commit()
            DataManager.numberOfCards = cards_number_picker.currentItemPosition + 3
            var intent = Intent(this, ProgressGameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
            this.finish()
        }
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
        super.onBackPressed()
        overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom)
    }
}
