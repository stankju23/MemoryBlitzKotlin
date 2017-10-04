package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Model.GamePlanItem
import com.example.stanislavcavajda.memoryblitz.R
import kotlinx.android.synthetic.main.activity_progress_game.view.*

/**
 * Created by stanislavcavajda on 04/10/2017.
 */
class GamePlan : BaseObservable {

    var list: ObservableArrayList<GamePlanItem> = ObservableArrayList<GamePlanItem>()

    constructor(itemList: ArrayList<GamePlanItem>,context: Context) {
        this.list.addAll(itemList)
    }

}