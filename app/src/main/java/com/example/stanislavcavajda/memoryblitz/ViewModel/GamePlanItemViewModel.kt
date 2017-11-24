package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.BaseObservable
import android.graphics.drawable.Drawable
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.GameTypeActivity
import com.example.stanislavcavajda.memoryblitz.ProgressGameActivity
import com.example.stanislavcavajda.memoryblitz.R
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.ClassicGameActivity
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.widget.FrameLayout


class GamePlanItemViewModel: BaseObservable {

    var name: Int = 0
    var image: ObservableField<Drawable> = ObservableField()
    var oldImage: Drawable? = null
    var clicked: Boolean = false
    var context: Context
    var isAnimation = ObservableBoolean(false)
    var background: ObservableField<Drawable> = ObservableField()

    var preferences:SharedPreferences? = null

    constructor(name: Int, image: Drawable, background: Drawable, clicked: Boolean, context: Context) {
        this.name = name
        this.oldImage = image
        this.image.set(image)
        this.clicked = clicked
        this.context = context
        this.background.set(background)
        this.preferences = (context as Activity).getSharedPreferences("highScore",Context.MODE_PRIVATE)
    }

    fun itemClick(v: View) {
        var found: Boolean = false
        var deleteIndex: Int = 0
        if (DataManager.canClick) {
            if (DataManager.typeSettingsActivity == Constants.PROGRESS_GAME) {
                if (!clicked) {
                    if (name == DataManager.listProgressGameCards[DataManager.actualIndex]) {
                        this.background.set(ContextCompat.getDrawable(context, R.drawable.card_background))
                        clicked = true
                        DataManager.pointsList.get(DataManager.actualIndex).correct.set(1)
                        DataManager.actualIndex++
                        notifyChange()
                        if (DataManager.actualIndex == DataManager.numberOfCards) {
                            DataManager.canClick = false
                            DataManager.actualScore ++
                            var fab = (context as Activity).findViewById<FloatingActionButton>(R.id.pause_btn)
                            fab.hide()
                            var handler = Handler()
                            handler.postDelayed(Runnable {
                                (context as Activity).finish()
                                var replyGame = Intent(context, ProgressGameActivity::class.java)
                                context.startActivity(replyGame)
                            },1500)

                        }
                    } else {
                        if (DataManager.progressGameHighScore < DataManager.actualScore) {
                            DataManager.progressGameHighScore = DataManager.actualScore
                            DataManager.actualScore = 0
                            var editor = preferences?.edit()
                            editor?.putInt(Constants.PROGRESS_GAME_HIGH_SCORE,DataManager.progressGameHighScore)
                            editor?.commit()
                        }
                        DataManager.actualScore = 0
                        var endGamePanel = (context as Activity).findViewById<FrameLayout>(R.id.end_slide_menu)
                        DataManager.canClick = false
                        while (DataManager.actualIndex <= DataManager.pointsList.size - 1) {
                            DataManager.pointsList.get(DataManager.actualIndex).correct.set(0)
                            DataManager.actualIndex++
                        }

                        notifyChange()

                        var fab = (context as Activity).findViewById<FloatingActionButton>(R.id.pause_btn)
                        fab.hide()

                        var handler = Handler()
                        handler.postDelayed(Runnable {
                            endGamePanel.animate().translationY(DataManager.endGameElementPosition.get(1)).duration = 500
                            var wantedCards = (context as Activity).findViewById<ConstraintLayout>(R.id.wantedCards)
                            wantedCards.alpha = 1f
                            var gamePlan = (context as Activity).findViewById<ConstraintLayout>(R.id.game_plan)
                            gamePlan.animate().translationY(2000f).duration = 1500
                        }, 700)
                    }


                }

            } else if (DataManager.typeSettingsActivity == Constants.CLASSIC_GAME) {
                if (!clicked) {
                    clicked = true
                    var handler = Handler()
                    handler.postDelayed(Runnable {
                        image.set(oldImage)
                        background.set(ContextCompat.getDrawable(context, R.drawable.card_background))
                    }, 250)
                    var i = 0
                    for (item in DataManager.wantedCards) {
                        if (item.name == name) {
                            found = true
                            deleteIndex = i
                        }
                        i++
                    }
                    notifyChange()
                    if (found) {
                        DataManager.wantedCards.removeAt(deleteIndex)
                    } else {
                        var pause = (context as Activity).findViewById<FloatingActionButton>(R.id.pause)
                        pause.hide()
                        DataManager.canClick = false
                        for (item in DataManager.classicGameGamePlan) {
                            if (item.clicked == false) {
                                item.clicked = true
                                handler.postDelayed(Runnable {
                                    item.image.set(item.oldImage)
                                    item.background.set(ContextCompat.getDrawable(context, R.drawable.card_background))
                                }, 250)
                            }
                        }
                        notifyChange()
                        if (DataManager.actualScore > DataManager.classicGameHighScore) {
                            DataManager.classicGameHighScore = DataManager.actualScore
                            var editor = preferences?.edit()
                            editor?.putInt(Constants.CLASSIC_GAME_HIGH_SCORE,DataManager.classicGameHighScore)
                            editor?.commit()
                        }
                        DataManager.actualScore = 0

                        handler.postDelayed(Runnable {
                            (context as Activity).finish()
                            var intent = Intent(context, GameTypeActivity::class.java)
                            context.startActivity(intent)
                            (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)

                        }, 3000)
                    }
                    if (DataManager.wantedCards.size == 0) {
                        DataManager.canClick = false
                        DataManager.actualScore++
                        var fab = (context as Activity).findViewById<FloatingActionButton>(R.id.pause)
                        fab.hide()
                        var handler1 = Handler()
                        handler1.postDelayed(Runnable {
                            (context as Activity).finish()
                            var intent = Intent(context, ClassicGameActivity::class.java)
                            context.startActivity(intent)
                            (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
                        }, 2000)
                    }
                }
            }
        }
    }
}