package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.os.Handler

/**
 * Created by stanislavcavajda on 04/10/2017.
 */
class GamePlan : BaseObservable {

    var list: ObservableArrayList<GamePlanItemViewModel> = ObservableArrayList<GamePlanItemViewModel>()

    constructor(itemViewModelList: ArrayList<GamePlanItemViewModel>, context: Context) {
        this.list.addAll(itemViewModelList)

        var handler = Handler()
        handler.postDelayed(Runnable {
            for (item in list) {
                item.isAnimation.set(true)
        } },2000)
    }

}