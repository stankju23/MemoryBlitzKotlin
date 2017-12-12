package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.view.View
import com.example.stanislavcavajda.memoryblitz.GameTypeActivity
import com.example.stanislavcavajda.memoryblitz.ProgressGameActivity
import com.example.stanislavcavajda.memoryblitz.R

/**
 * Created by stanislavcavajda on 04/10/2017.
 */
class GamePlan : BaseObservable {

    var list: ObservableArrayList<GamePlanItemViewModel> = ObservableArrayList<GamePlanItemViewModel>()
    var context:Context? = null
    constructor(itemViewModelList: ArrayList<GamePlanItemViewModel>, context: Context) {
        this.list.addAll(itemViewModelList)
        this.context = context
    }

    fun retryGame(view: View) {
        (context as Activity).finish()
        var replyGame = Intent(context, ProgressGameActivity::class.java)
        context?.startActivity(replyGame)
        (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
    }

    fun endGame(view: View) {
        (context as Activity).finish()
        var endGame = Intent(context, GameTypeActivity::class.java)
        context?.startActivity(endGame)
        (context as Activity).overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
    }

}