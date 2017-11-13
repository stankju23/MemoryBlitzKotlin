package com.example.stanislavcavajda.memoryblitz

import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.Gravity
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.ViewModel.GamePlan
import com.example.stanislavcavajda.memoryblitz.ViewModel.GamePlanItemViewModel
import com.example.stanislavcavajda.memoryblitz.databinding.ActivityClassicGame2x2Binding
import kotlinx.android.synthetic.main.activity_classic_game_2x2.*
import com.example.stanislavcavajda.memoryblitz.databinding.TwoXTwoBinding
import com.example.stanislavcavajda.memoryblitz.databinding.TwoXThreeBinding
import com.example.stanislavcavajda.memoryblitz.databinding.ThreeXThreeBinding
import com.example.stanislavcavajda.memoryblitz.databinding.OneCardBinding
import com.example.stanislavcavajda.memoryblitz.databinding.TwoCardsBinding
import com.example.stanislavcavajda.memoryblitz.databinding.ThreeCardsBinding
import com.example.stanislavcavajda.memoryblitz.Model.WantedCardModel

import android.view.View
import com.example.stanislavcavajda.memoryblitz.ViewModel.TimerViewModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.WantedCardsListViewModel
import com.mancj.slideup.SlideUp
import com.mancj.slideup.SlideUpBuilder
import kotlinx.android.synthetic.main.activity_progress_game.*
import java.util.*
import kotlin.collections.ArrayList




class ClassicGameActivity : AppCompatActivity() {
    lateinit var viewModel: GamePlan
    var waiting = true
    var timer1: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        DataManager.classicGameGamePlan.clear()

        DataManager.wantedCards.clear()
        DataManager.canClick = false
        DataManager.pauseMillis = (DataManager.timeToMemorize + 2) * 1000L


        var binding: ActivityClassicGame2x2Binding = DataBindingUtil.setContentView(this, R.layout.activity_classic_game_2x2)
        var timerViewModel = TimerViewModel("0:0${DataManager.pauseMillis / 1000}", "MEMORIZE", "${DataManager.actualScore}")
        binding.viewModel = timerViewModel

        var timer = object : CountDownTimer(DataManager.pauseMillis, 1000) {

            override fun onTick(millis: Long) {
                if (millis / 1000 == 1L) {
                    timerViewModel.timer.set("0:00")
                } else {
                    timerViewModel.timer.set("0:0${(millis / 1000 - 1).toString()}")
                }
                DataManager.pauseMillis = millis
            }

            override fun onFinish() {
            }
        }
        timer.start()


        when (DataManager.cardMatrix) {
            Constants.CLASSIC_GAME_2x2 -> {
                viewStub.layoutResource = R.layout.two_x_two
            }
            Constants.CLASSIC_GAME_2x3 -> {
                viewStub.layoutResource = R.layout.two_x_three
            }
            Constants.CLASSIC_GAME_3x3 -> {
                viewStub.layoutResource = R.layout.three_x_three
            }
        }
        when (DataManager.numberOfWantedCards) {
            Constants.WANTED_CARDS_ONE -> {
                viewStubWantedCards.layoutResource = R.layout.one_card
            }
            Constants.WANTED_CARDS_TWO -> {
                viewStubWantedCards.layoutResource = R.layout.two_cards
            }
            Constants.WANTED_CARDS_THREE -> {
                viewStubWantedCards.layoutResource = R.layout.three_cards
            }
        }

        var cardList = ArrayList<GamePlanItemViewModel>()
        var i = 9
        for (i in 1..9) {
            var resId = resources.getIdentifier("${DataManager.actualCheckedGraphicPack}_$i", "drawable", packageName)
            cardList.add(GamePlanItemViewModel(i, ContextCompat.getDrawable(this, resId), ContextCompat.getDrawable(this, R.drawable.card_background), false, this))
        }

        shuffleCardList(cardList)

        for (i in 0..DataManager.classicGameNumberOfCards - 1) {
            DataManager.classicGameGamePlan.add(cardList.get(i))
        }

        binding.viewStub.setOnInflateListener { viewStub,
                                                view ->
            when (DataManager.cardMatrix) {
                Constants.CLASSIC_GAME_2x2 -> {
                    var viewStubBinding: TwoXTwoBinding = DataBindingUtil.bind(view)
                    ; viewStubBinding.viewModel = GamePlan(DataManager.classicGameGamePlan, this)
                }
                Constants.CLASSIC_GAME_2x3 -> {
                    var viewStubBinding: TwoXThreeBinding = DataBindingUtil.bind(view)
                    ; viewStubBinding.viewModel = GamePlan(DataManager.classicGameGamePlan, this)
                }
                Constants.CLASSIC_GAME_3x3 -> {
                    var viewStubBinding: ThreeXThreeBinding = DataBindingUtil.bind(view)
                    ; viewStubBinding.viewModel = GamePlan(DataManager.classicGameGamePlan, this)
                }
            }

        }

        shuffleCardList(DataManager.classicGameGamePlan)

        for (i in 0..DataManager.numberOfWantedCards) {
            DataManager.wantedCards.add(i, WantedCardModel(cardList.get(i).name, ContextCompat.getDrawable(this, R.drawable.empty)))
        }

        binding.viewStubWantedCards.setOnInflateListener { viewStub,
                                                           view ->
            when (DataManager.numberOfWantedCards) {
                Constants.WANTED_CARDS_ONE -> {
                    var viewStubBinding: OneCardBinding = DataBindingUtil.bind(view)
                    ; viewStubBinding.viewModel = WantedCardsListViewModel(DataManager.wantedCards)
                }
                Constants.WANTED_CARDS_TWO -> {
                    var viewStubBinding: TwoCardsBinding = DataBindingUtil.bind(view)
                    ; viewStubBinding.viewModel = WantedCardsListViewModel(DataManager.wantedCards)
                }
                Constants.WANTED_CARDS_THREE -> {
                    var viewStubBinding: ThreeCardsBinding = DataBindingUtil.bind(view)
                    ; viewStubBinding.viewModel = WantedCardsListViewModel(DataManager.wantedCards)
                }
            }
        }

        var handler = Handler()
        handler.postDelayed(Runnable {
            for (item in DataManager.classicGameGamePlan) {
                item.isAnimation.set(true)
            }
        }, DataManager.pauseMillis - 2000)

        var handler2 = Handler()
        handler2.postDelayed(Runnable {
            for (i in 0..DataManager.wantedCards.size - 1) {
                var resId = resources.getIdentifier("${DataManager.actualCheckedGraphicPack}_${cardList.get(i).name}", "drawable", packageName)
                DataManager.wantedCards[i].image.set(ContextCompat.getDrawable(this, resId))
            }
        }, DataManager.pauseMillis - 1500L)

        viewStub.inflate()
        viewStubWantedCards.inflate()
        var hanlder = Handler()
        hanlder.postDelayed(Runnable {
            for (item in DataManager.classicGameGamePlan) {
                var handler1 = Handler()
                handler1.postDelayed(Runnable {
                    item.oldImage = item.image.get()
                    item.background.set(ContextCompat.getDrawable(this, R.drawable.hide_card))
                    item.image.set(ContextCompat.getDrawable(this, R.drawable.empty))
                }, 250)
            }
            DataManager.canClick = true
            waiting = false
        }, DataManager.pauseMillis - 2000L)

        fun resumeTimer() {
            pause.show()

            if (waiting) {
                timer1 = object : CountDownTimer(DataManager.pauseMillis, 1000) {

                    override fun onTick(millis: Long) {
                        if (millis / 1000 == 1L) {
                            timerViewModel.timer.set("0:00")
                        } else {
                            timerViewModel.timer.set("0:0${(millis / 1000 - 1).toString()}")
                        }
                        DataManager.pauseMillis = millis
                    }

                    override fun onFinish() {
                    }

                }
                timer1?.start()

                handler.postDelayed(Runnable {
                    for (item in DataManager.classicGameGamePlan) {
                        item.isAnimation.set(true)
                    }
                }, DataManager.pauseMillis - 2000)

                handler2.postDelayed(Runnable {
                    for (i in 0..DataManager.wantedCards.size - 1) {
                        var resId = resources.getIdentifier("${DataManager.actualCheckedGraphicPack}_${cardList.get(i).name}", "drawable", packageName)
                        DataManager.wantedCards[i].image.set(ContextCompat.getDrawable(this, resId))
                    }
                }, DataManager.pauseMillis - 1500L)

                hanlder.postDelayed(Runnable {
                    for (item in DataManager.classicGameGamePlan) {
                        var handler1 = Handler()
                        handler1.postDelayed(Runnable {
                            item.background.set(ContextCompat.getDrawable(this, R.drawable.hide_card))
                            item.image.set(ContextCompat.getDrawable(this, R.drawable.empty))
                        }, 250)
                    }
                    DataManager.canClick = true
                    waiting = false
                }, DataManager.pauseMillis - 2000)
            }
        }


        var slideUp = SlideUpBuilder(slide_view).withStartState(SlideUp.State.HIDDEN).
                withListeners(SlideUp.Listener.Visibility { visibility ->
                    if (visibility == View.GONE) {
                        if(waiting) {
                            for (item in DataManager.classicGameGamePlan) {
                                item.image.set(item.oldImage)
                            }
                        }
                        pause.show()
                        if(waiting) {
                            resumeTimer()
                        }
                    } else {
                        for (item in DataManager.classicGameGamePlan) {
                            item.image.set(ContextCompat.getDrawable(this, R.drawable.empty))
                        }
                    }
                })
                .withStartGravity(Gravity.BOTTOM).build()

        pause.setOnClickListener {
            slideUp.show();
            pause.hide()
            if (waiting) {
                timer.cancel()
                handler.removeMessages(0)
                handler2.removeMessages(0)
                hanlder.removeMessages(0)
            }
            if (timer1 != null) {
                timer1?.cancel()
                timer1 = null
            }
        }


        resume.setOnClickListener {
            slideUp.hide()
        }

        retry.setOnClickListener {
            this.finish()
            var intent = Intent(this, ClassicGameActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }

        end.setOnClickListener {
            this.finish()
            var intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
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

    fun shuffleCardList(cardList: ArrayList<GamePlanItemViewModel>) {
        val rg: Random = Random()
        for (i in 0..cardList.size - 1) {
            val randomPosition = rg.nextInt(cardList.size)
            val tmp: GamePlanItemViewModel = cardList[i]
            cardList[i] = cardList[randomPosition]
            cardList[randomPosition] = tmp
        }

    }

    override fun onBackPressed() {
    }

}