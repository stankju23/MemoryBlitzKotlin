package com.example.stanislavcavajda.memoryblitz

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.constraint.ConstraintSet
import android.support.v4.content.ContextCompat
import android.transition.TransitionManager
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.Model.GamePlanItem
import com.example.stanislavcavajda.memoryblitz.ViewModel.GamePlan
import com.example.stanislavcavajda.memoryblitz.databinding.ActivityProgressGameBinding
import kotlinx.android.synthetic.main.activity_progress_game.*
import java.util.*
import android.view.ViewTreeObserver
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.Animator
import android.opengl.ETC1.getWidth
import android.support.v4.view.ViewCompat.setPivotX
import android.opengl.ETC1.getHeight
import android.support.v4.view.ViewCompat.setPivotY




class ProgressGameActivity : AppCompatActivity() {

    var list : ArrayList<Int>? = null
    var index = 0
    var seconds: Long = 0

    lateinit var left: ImageView
    lateinit var right: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityProgressGameBinding = DataBindingUtil.setContentView(this,R.layout.activity_progress_game)

        DataManager.canClick = false

        DataManager.actualIndex = 0

        list = arrayListOf<Int>(1,5,6)

        game_plan.y = 2000f
        flying_card.x = 2000f

        println(main_plan_layout.width)

        game_plan.animate().translationY(main_plan_layout.height/2f).duration = 1000


        var handler = Handler()
        var cardList = ArrayList<GamePlanItem>()
        handler.postDelayed(Runnable { game_plan.animate().translationY(2000f).duration = 1000},2000)


        var i = 9
        while (i >= 1) {
            var resId = resources.getIdentifier("summer_$i","drawable",packageName)
            cardList.add(GamePlanItem(i,ContextCompat.getDrawable(this,resId),false,this))
            i--
        }

        val rg : Random = Random()

        for (i in 0..cardList.size - 1) {
            val randomPosition = rg.nextInt(cardList.size)
            val tmp : GamePlanItem = cardList[i]
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

        for (i in 0..DataManager.numberOfCards-1){
            seconds = seconds + 2500
            handler.postDelayed(Runnable { moveCard("summer_${DataManager.listProgressGameCards.get(i)}") },seconds)
        }


        handler.postDelayed(Runnable { game_plan.animate().translationY(0f).duration = 1000 }, seconds + 2000)
        handler.postDelayed(Runnable { DataManager.canClick = true },seconds)
        binding.viewModel = viewModel
        print(cardList)

    }

    fun moveCard(name:String) {
        var resourceId = resources.getIdentifier(name, "drawable",packageName)
        flying_card.setImageResource(resourceId)
        flying_card.x = 2000f
        flying_card.animate().translationX(0f).duration = 1000
        var handler1 = Handler()
        handler1.postDelayed( Runnable { flying_card.animate().translationX(- 2000f).duration = 1000 },2000)
        index++
    }



}
