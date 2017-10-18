package com.example.stanislavcavajda.memoryblitz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardViewModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardListViewModel
import com.example.stanislavcavajda.memoryblitz.databinding.ActivityEndlessGameSettingsBinding
import kotlinx.android.synthetic.main.activity_endless_game_settings.*


class EndlessGameSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()


        var binding: ActivityEndlessGameSettingsBinding = DataBindingUtil.setContentView(this,R.layout.activity_endless_game_settings)

        DataManager.graphicPacks.clear()

        for (item in DataManager.graphicPacksNames) {
            if (item == DataManager.actualCheckedGraphicPack) {
                DataManager.graphicPacks.add(CardViewModel("${item}_1", this, true, item))
            } else {
                DataManager.graphicPacks.add(CardViewModel("${item}_1", this, false, item))
            }
        }

        var viewModel = CardListViewModel(DataManager.graphicPacks,this)
        binding.viewModel = viewModel

        cards_number_picker.data = arrayListOf("3 cards","4 cards", "5 cards", "6 cards", "7 cards", "8 cards", "9 cards")

        button2.setOnClickListener {
            DataManager.numberOfCards = cards_number_picker.currentItemPosition + 3
            val intent = Intent(this, ProgressGameActivity::class.java)
            startActivity(intent)
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
