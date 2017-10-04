package com.example.stanislavcavajda.memoryblitz

import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Bindings.ImageViewBindingComponent
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.Model.Card
import com.example.stanislavcavajda.memoryblitz.ViewModel.CardViewModel
import com.example.stanislavcavajda.memoryblitz.databinding.ActivitySpeedGameBinding
import kotlinx.android.synthetic.main.activity_speed_game.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import me.tatarka.bindingcollectionadapter2.LayoutManagers

class SpeedGameSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        var binding:ActivitySpeedGameBinding = DataBindingUtil.setContentView(this,R.layout.activity_speed_game)

        DataManager.graphicPacks.clear()
        var pocet = 9
        while (pocet >= 1) {
            var id = resources.getIdentifier("summer_$pocet", "drawable", packageName)
            DataManager.graphicPacks.add(Card(id, this, false, "summer"))
            pocet--
        }

        seconds.text = DataManager.seconds.toString()

        var viewModel = CardViewModel(DataManager.graphicPacks,this)
        binding.viewModel = viewModel

        plus_button.setOnClickListener {
            if (DataManager.seconds < 10) {
                DataManager.seconds++
                seconds.text = DataManager.seconds.toString()
            }
        }

        minus_button.setOnClickListener {
            if (DataManager.seconds > 0) {
                DataManager.seconds--
                seconds.text = DataManager.seconds.toString()
            }
        }

        OverScrollDecoratorHelper.setUpOverScroll(scroll_view)
        cards_to_remember_segmented_group.setTintColor(ContextCompat.getColor(this,R.color.gradient_start),ContextCompat.getColor(this,R.color.white))
        card_matrix_to_remember_segmented_group.setTintColor(ContextCompat.getColor(this,R.color.gradient_start),ContextCompat.getColor(this,R.color.white))
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
