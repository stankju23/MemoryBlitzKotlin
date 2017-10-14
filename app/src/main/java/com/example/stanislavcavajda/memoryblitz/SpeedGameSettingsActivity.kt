package com.example.stanislavcavajda.memoryblitz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardViewModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardListViewModel
import com.example.stanislavcavajda.memoryblitz.databinding.ActivitySpeedGameBinding
import kotlinx.android.synthetic.main.activity_speed_game.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class SpeedGameSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        var binding:ActivitySpeedGameBinding = DataBindingUtil.setContentView(this,R.layout.activity_speed_game)

        DataManager.graphicPacks.clear()
        var pocet = 9
        while (pocet >= 1) {
            var id = resources.getIdentifier("summer_$pocet", "drawable", packageName)
            DataManager.graphicPacks.add(CardViewModel(id, this, false, "summer"))
            pocet--
        }

        seconds.text = DataManager.timeToMemorize.toString()

        var viewModel = CardListViewModel(DataManager.graphicPacks,this)
        binding.viewModel = viewModel

        plus_button.setOnClickListener {
            if (DataManager.timeToMemorize < 10) {
                DataManager.timeToMemorize++
                seconds.text = DataManager.timeToMemorize.toString()
            }
        }

        minus_button.setOnClickListener {
            if (DataManager.timeToMemorize > 0) {
                DataManager.timeToMemorize--
                seconds.text = DataManager.timeToMemorize.toString()
            }
        }

        OverScrollDecoratorHelper.setUpOverScroll(scroll_view)
        cards_to_remember_segmented_group.setTintColor(ContextCompat.getColor(this,R.color.gradient_start),ContextCompat.getColor(this,R.color.white))
        card_matrix_to_remember_segmented_group.setTintColor(ContextCompat.getColor(this,R.color.gradient_start),ContextCompat.getColor(this,R.color.white))

        button2.setOnClickListener(View.OnClickListener {
            var intent = Intent(this,ClassicGameActivity::class.java)
            startActivity(intent)
        })


        when(DataManager.cardMatrix) {
            0 ->  {card_matrix_to_remember_segmented_group.check(R.id.button31);DataManager.classicGameNumberOfCards = 4}
            1 ->  {card_matrix_to_remember_segmented_group.check(R.id.button32);DataManager.classicGameNumberOfCards = 6}
            2 ->  {card_matrix_to_remember_segmented_group.check(R.id.button33);DataManager.classicGameNumberOfCards = 9}
        }

        when(DataManager.numberOfWantedCards) {
            0 ->  cards_to_remember_segmented_group.check(R.id.button21)
            1 ->  cards_to_remember_segmented_group.check(R.id.button22)
            2 ->  cards_to_remember_segmented_group.check(R.id.button23)
        }

        card_matrix_to_remember_segmented_group.setOnCheckedChangeListener {
            radioGroup,
            i -> when(i) {
                R.id.button31 -> {DataManager.cardMatrix = 0 ; DataManager.classicGameNumberOfCards = 4}
                R.id.button32 -> {DataManager.cardMatrix = 1 ; DataManager.classicGameNumberOfCards = 6}
                R.id.button33 -> {DataManager.cardMatrix = 2 ; DataManager.classicGameNumberOfCards = 9}
            }
            println(DataManager.cardMatrix)
        }

        cards_to_remember_segmented_group.setOnCheckedChangeListener {
            radioGroup,
            i ->  when(i) {
                R.id.button21 -> DataManager.numberOfWantedCards = 0
                R.id.button22 -> DataManager.numberOfWantedCards = 1
                R.id.button23 -> DataManager.numberOfWantedCards = 2

            }
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
        super.onBackPressed()
        overridePendingTransition(R.anim.abc_slide_in_bottom,R.anim.abc_slide_out_bottom)
    }
}
