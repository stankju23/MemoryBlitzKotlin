package com.example.stanislavcavajda.memoryblitz

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.ViewModel.GamePlanItemViewModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.GamePlan
import com.example.stanislavcavajda.memoryblitz.databinding.ActivityProgressGameBinding
import kotlinx.android.synthetic.main.activity_progress_game.*
import java.util.*
import android.widget.ImageView
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import kotlinx.android.synthetic.main.activity_classic_game_2x2.*


class ProgressGameActivity : AppCompatActivity() {

    var list : ArrayList<Int>? = null
    var index = 0
    var seconds: Long = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityProgressGameBinding = DataBindingUtil.setContentView(this,R.layout.activity_progress_game)
        setFullScreen()
        DataManager.canClick = false

        DataManager.actualIndex = 0

        list = arrayListOf<Int>(1,5,6)

        game_plan.y = 2000f
        flying_card.x = 2000f

        println(main_plan_layout.width)

        game_plan.animate().translationY(main_plan_layout.height/2f).duration = 1000


        var handler = Handler()
        var cardList = ArrayList<GamePlanItemViewModel>()
        handler.postDelayed(Runnable { game_plan.animate().translationY(2000f).duration = 1000},2000)


        for (i in 1..9) {
            var resId = resources.getIdentifier("${DataManager.actualCheckedGraphicPack}_$i","drawable",packageName)
            cardList.add(GamePlanItemViewModel(i, ContextCompat.getDrawable(this, resId), ContextCompat.getDrawable(this, R.drawable.card_background), false, this))
        }

        val rg : Random = Random()

        for (i in 0..cardList.size - 1) {
            val randomPosition = rg.nextInt(cardList.size)
            val tmp : GamePlanItemViewModel = cardList[i]
            cardList[i] = cardList[randomPosition]
            cardList[randomPosition] = tmp
        }


        for (i in 0..DataManager.listProgressGameCards.size - 1) {
            val randomPosition = rg.nextInt(DataManager.listProgressGameCards.size)
            val tmp : Int = DataManager.listProgressGameCards[i]
            DataManager.listProgressGameCards[i] = DataManager.listProgressGameCards[randomPosition]
            DataManager.listProgressGameCards[randomPosition] = tmp
        }


        var viewModel = GamePlan(cardList,this)
        var runnable: Runnable

        for (i in 0..DataManager.numberOfCards-1){
            seconds = seconds + 1750
            runnable = Runnable { moveCard("${DataManager.actualCheckedGraphicPack}_${DataManager.listProgressGameCards.get(i)}") }
            handler.postDelayed(runnable,seconds)
        }

        var slideUp = SlideUpBuilder(slide_view_progress).withStartState(SlideUp.State.HIDDEN).withStartGravity(Gravity.BOTTOM).build()

        pause_btn.setOnClickListener {
            slideUp.show()
            pause_btn.hide()
        }

        resume_btn.setOnClickListener {
            slideUp.hide()
            pause_btn.show()
        }

        handler.postDelayed(Runnable { game_plan.animate().translationY(0f).duration = 1000 }, seconds + 1500)
        handler.postDelayed(Runnable { DataManager.canClick = true },seconds)

        binding.viewModel = viewModel
        print(cardList)

    }

    fun moveCard(name:String) {
        var resourceId = resources.getIdentifier(name, "drawable",packageName)
        flying_card.setImageResource(resourceId)
        flying_card.x = 2000f
        flying_card.animate().translationX(0f).duration = 500
        var handler1 = Handler()
        handler1.postDelayed( Runnable { flying_card.animate().translationX(- 2000f).duration = 500 },1250)
        index++
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
