package com.example.stanislavcavajda.memoryblitz.Model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.graphics.drawable.Drawable
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.GameTypeActivity
import com.example.stanislavcavajda.memoryblitz.ProgressGameActivity
import com.example.stanislavcavajda.memoryblitz.R

/**
 * Created by stanislavcavajda on 04/10/2017.
 */
class GamePlanItem(var name: Int ,var image: Drawable, var clicked: Boolean,var context: Context): BaseObservable() {

    fun itemClick(v: View) {
        if (DataManager.canClick) {
            if (!clicked) {
                if (name == DataManager.listProgressGameCards[DataManager.actualIndex]) {
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
        }
    }
}