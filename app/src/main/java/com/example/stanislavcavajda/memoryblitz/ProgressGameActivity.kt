package com.example.stanislavcavajda.memoryblitz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.databinding.ActivityProgressGameBinding
import kotlinx.android.synthetic.main.activity_progress_game.*
import com.example.stanislavcavajda.memoryblitz.Model.WantedCardModel
import java.util.*
import android.widget.ImageView
import com.example.stanislavcavajda.memoryblitz.ViewModel.*
import com.google.android.gms.drive.events.ChangeListener
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import kotlin.collections.ArrayList
import kotlinx.android.synthetic.main.activity_classic_game_2x2.*
import android.widget.Toast




class ProgressGameActivity : AppCompatActivity() {

    var list : ArrayList<Int>? = null
    var index = 0
    var seconds: Long = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityProgressGameBinding = DataBindingUtil.setContentView(this,R.layout.activity_progress_game)
        DataManager.pointsList.clear()


        for (i  in 0..DataManager.numberOfCards - 1 ) {
            DataManager.pointsList.add(PointViewModel(3))
        }

        binding.pointListViewModel = PointListViewModel(DataManager.pointsList)

        setFullScreen()
        DataManager.canClick = false

        DataManager.actualIndex = 0

        list = arrayListOf<Int>(1,5,6)

        game_plan.y = 2000f
        flying_card_layour.x = 2000f

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
        var wantedCards: ArrayList<WantedCardModel> = ArrayList()
        for (i in 0..DataManager.numberOfCards-1) {
            var identifier = resources.getIdentifier("${DataManager.actualCheckedGraphicPack}_${DataManager.listProgressGameCards.get(i)}"
                    ,"drawable",packageName)
            wantedCards.add(WantedCardModel(DataManager.listProgressGameCards.get(i),ContextCompat.getDrawable(this,identifier)))
        }

        binding.wantedCardListViewModel = ProgressGameCardListViewModel(wantedCards)

        var viewModel = GamePlan(cardList,this)
        var runnable: Runnable

        for (i in 0..DataManager.numberOfCards-1){
            seconds = seconds + 1750
            runnable = Runnable { moveCard("${DataManager.actualCheckedGraphicPack}_${DataManager.listProgressGameCards.get(i)}");
                                    DataManager.pointsList.get(i).correct.set(2);
                                    var recyclerView = findViewById<RecyclerView>(R.id.point_recycler_view)
                                    recyclerView.adapter.notifyItemChanged(i)}
            handler.postDelayed(runnable,seconds)
        }


        handler.postDelayed(Runnable { game_plan.animate().translationY(0f).duration = 1000 }, seconds + 1500)
        handler.postDelayed(Runnable { DataManager.canClick = true },seconds)

        slide_view_progress_game.bringToFront()

        var slideUp = SlideUpBuilder(slide_view_progress_game).withStartState(SlideUp.State.HIDDEN).
                withListeners(SlideUp.Listener.Visibility {
                    visibility -> if (visibility == View.GONE) {
                    pause_btn.show()
                }
                })
                .withStartGravity(Gravity.BOTTOM)
                .build()

        DataManager.endGameElementPosition.set(0,end_slide_menu.x)
        DataManager.endGameElementPosition.set(1,end_slide_menu.y)

        end_slide_menu.translationY = -1000f


        activity_progress_resume.setOnClickListener {
            slideUp.hide()
        }

        activity_progress_end.setOnClickListener {
            this.finish()
            var intent = Intent(this,StartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }


        pause_btn.setOnClickListener {
            slideUp.show()
            pause_btn.hide()

        }

        binding.viewModel = viewModel
        print(cardList)

    }

    fun moveCard(name:String) {
        var resourceId = resources.getIdentifier(name, "drawable",packageName)
        flying_card.setImageResource(resourceId)
        flying_card_layour.x = 2000f
        flying_card_layour.animate().translationX(0f).duration = 500
        var handler1 = Handler()
        handler1.postDelayed( Runnable { flying_card_layour.animate().translationX(- 2000f).duration = 500 },1250)
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
