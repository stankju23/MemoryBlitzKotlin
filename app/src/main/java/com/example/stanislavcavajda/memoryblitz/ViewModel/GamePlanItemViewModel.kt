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
import android.widget.TextView


class GamePlanItemViewModel: BaseObservable {

    var name: Int = 0
    var image: ObservableField<Drawable> = ObservableField()
    var oldImage: Drawable? = null
    var clicked: Boolean = false
    var context: Context
    var isAnimation = ObservableBoolean(false)
    var background: ObservableField<Drawable> = ObservableField()

    var preferences:SharedPreferences? = null

    var handler: Handler? = null

    constructor(name: Int, image: Drawable, background: Drawable, clicked: Boolean, context: Context) {
        this.name = name
        this.oldImage = image
        this.image.set(image)
        this.clicked = clicked
        this.context = context
        this.background.set(background)
        this.preferences = (context as Activity).getSharedPreferences("highScore",Context.MODE_PRIVATE)
        this.handler = Handler()
    }

    fun itemClick(v: View) {
        var found: Boolean = false
        var deleteIndex: Int = 0

        if (DataManager.canClick) {

            // zistujem typ hry
            if (DataManager.typeSettingsActivity == Constants.PROGRESS_GAME) {

                //zistujem ci uz tlacitko bolo stlacene
                if (!clicked) {

                    // ci bolo stlacene spravne tlacitko
                    if (name == DataManager.listProgressGameCards[DataManager.actualIndex]) {

                        //nastujem pozadie stalceneho uhadnuteho tlacitka
                        this.background.set(ContextCompat.getDrawable(context, R.drawable.card_background))
                        clicked = true
                        DataManager.pointsList.get(DataManager.actualIndex).correct.set(1)
                        DataManager.actualIndex++
                        notifyChange()

                        // zistujem ci som nenasiel vsetky
                        if (DataManager.actualIndex == DataManager.numberOfCards) {
                            DataManager.canClick = false
                            DataManager.actualScore += DataManager.scoreMultiplicierProgressivGame().toInt()
                            var fab = (context as Activity).findViewById<FloatingActionButton>(R.id.pause_btn)
                            fab.hide()

                            // nasiel som vsetky spustam znova dalsiu hru a pripocitavam skore
                            startNewActivity(1500,Intent(context, ProgressGameActivity::class.java))

                        }
                    } else {

                        /* prehral som
                           zistujem ci som nenahral zatial najvyssie skore
                           ak ano ukladam do pamate
                        */
                        saveHighScore(Constants.PROGRESS_GAME_HIGH_SCORE,DataManager.actualScore,DataManager.progressGameHighScore)
                        DataManager.actualScore = 0


                        // nastavujem farbu neuhadnutym gulickam na cerveno
                        DataManager.actualScore = 0
                        var endGamePanel = (context as Activity).findViewById<FrameLayout>(R.id.end_slide_menu)
                        DataManager.canClick = false
                        while (DataManager.actualIndex <= DataManager.pointsList.size - 1) {
                            DataManager.pointsList.get(DataManager.actualIndex).correct.set(0)
                            DataManager.actualIndex++
                        }
                        notifyChange()

                        // schovavanie pause buttonu
                        var fab = (context as Activity).findViewById<FloatingActionButton>(R.id.pause_btn)
                        fab.hide()

                        // schovavam hraciu plochu a vysuvam slide menu na ukoncenie alebo restartovanie hry
                        handler?.postDelayed(Runnable {
                            endGamePanel.animate().translationY(DataManager.endGameElementPosition.get(1)).duration = 500
                            var wantedCards = (context as Activity).findViewById<ConstraintLayout>(R.id.wantedCards)
                            wantedCards.alpha = 1f
                            var gamePlan = (context as Activity).findViewById<ConstraintLayout>(R.id.game_plan)
                            gamePlan.animate().translationY(2000f).duration = 1500
                        }, 700)
                    }


                }

            } else if (DataManager.typeSettingsActivity == Constants.CLASSIC_GAME) {
                var memorizeText = (context as Activity).findViewById<TextView>(R.id.memorizeText)
                //zistujem ci uz tlacitko bolo stlacene
                if (!clicked) {
                    clicked = true

                    // zobrazim tlacitko ktore som stlacil
                    handler?.postDelayed(Runnable {
                        image.set(oldImage)
                        background.set(ContextCompat.getDrawable(context, R.drawable.card_background))
                    }, 250)

                    // hladam ci som stlacil spravne tlacitko ak najdem nastavim found na true
                    var i = 0
                    for (item in DataManager.wantedCards) {
                        if (item.name == name) {
                            found = true
                            deleteIndex = i
                        }
                        i++
                    }
                    notifyChange()

                    // zistujem ci som stlacil tlacitko ktore hladam
                    if (found) {

                        // zmazem z arraylistu hladanych
                        DataManager.wantedCards.removeAt(deleteIndex)
                        memorizeText.setText(R.string.right)

                    } else {
                        memorizeText.setText(R.string.end)
                        // nenasiel som , schovavam pause button a zobrazujem vsetky tlacitka
                        var pause = (context as Activity).findViewById<FloatingActionButton>(R.id.pause)
                        pause.hide()
                        DataManager.canClick = false
                        for (item in DataManager.classicGameGamePlan) {
                            if (item.clicked == false) {
                                item.clicked = true
                                handler?.postDelayed(Runnable {
                                    item.image.set(item.oldImage)
                                    item.background.set(ContextCompat.getDrawable(context, R.drawable.card_background))
                                }, 250)
                            }
                        }
                        notifyChange()

                        // kontrolujem ci som nenahral najvyssie skore, ak ano hned aj ukladam
                        saveHighScore(Constants.CLASSIC_GAME_HIGH_SCORE,DataManager.actualScore,DataManager.classicGameHighScore)
                        DataManager.actualScore = 0

                        // prehral som spustam aktivitu pre vyber typu hry
                        startNewActivity(3000,Intent(context, GameTypeActivity::class.java))
                    }

                    // zistujem ci som vyhral celu hru
                    if (DataManager.wantedCards.size == 0) {
                        DataManager.canClick = false
                        DataManager.actualScore += DataManager.scoreMultiplicierClassicGame().toInt()

                        // schovam pause button
                        var fab = (context as Activity).findViewById<FloatingActionButton>(R.id.pause)
                        fab.hide()

                        // spustam novu hru so zvysenym skore
                        startNewActivity(2000,Intent(context, ClassicGameActivity::class.java))
                    }
                }
            }
        }
    }

    fun startNewActivity(delay:Long, activity:Intent) {
        handler?.postDelayed(Runnable {
            (context as Activity).finish()
            context.startActivity(activity)
            (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
        }, delay)
    }

    fun saveHighScore(identifier:String, score:Int, actualHighScore:Int) {
        if (score > actualHighScore) {
            if (identifier == Constants.PROGRESS_GAME_HIGH_SCORE) {
                DataManager.progressGameHighScore = score
            } else {
                DataManager.classicGameHighScore = score
            }
            var editor = preferences?.edit()
            editor?.putInt(identifier,score)
            editor?.commit()
        }
    }
}