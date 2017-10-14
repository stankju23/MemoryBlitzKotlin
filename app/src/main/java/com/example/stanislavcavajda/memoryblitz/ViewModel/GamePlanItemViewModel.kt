package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.GameTypeActivity
import com.example.stanislavcavajda.memoryblitz.ProgressGameActivity
import com.example.stanislavcavajda.memoryblitz.R
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.ClassicGameActivity
import com.example.stanislavcavajda.memoryblitz.Model.WantedCardModel
import com.example.stanislavcavajda.memoryblitz.SpeedGameSettingsActivity
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.os.Handler
import android.provider.ContactsContract
import android.support.v4.content.ContextCompat


class GamePlanItemViewModel: BaseObservable {

    var name: Int = 0
    var image: ObservableField<Drawable> = ObservableField()
    lateinit var oldImage: Drawable
    var clicked: Boolean = false
    var context: Context
    var isAnimation = ObservableBoolean(false)
    var background: ObservableField<Drawable> = ObservableField()

    constructor(name: Int, image:Drawable,background:Drawable,clicked: Boolean,context: Context) {
        this.name = name
        this.image.set(image)
        this.clicked = clicked
        this.context = context
        this.background.set(background)
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
                        DataManager.actualIndex++
                        notifyChange()
                    } else {
                        var intent = Intent(context, GameTypeActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
                    }

                    if (DataManager.actualIndex == DataManager.numberOfCards) {
                        var replyGame = Intent(context, ProgressGameActivity::class.java)
                        context.startActivity(replyGame)
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
                            DataManager.actualScore = 0
                        }

                        handler.postDelayed(Runnable {
                            (context as Activity).finish()
                            var intent = Intent(context,SpeedGameSettingsActivity::class.java)
                            context.startActivity(intent)
                            (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)

                        },3000)
                    }
                    if (DataManager.wantedCards.size == 0) {
                        DataManager.actualScore++
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