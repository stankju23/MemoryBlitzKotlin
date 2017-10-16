package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.os.Handler
import com.example.stanislavcavajda.memoryblitz.Data.DataManager

/**
 * Created by stanislavcavajda on 04/10/2017.
 */
class GamePlan : BaseObservable {

    var list: ObservableArrayList<GamePlanItemViewModel> = ObservableArrayList<GamePlanItemViewModel>()

    constructor(itemViewModelList: ArrayList<GamePlanItemViewModel>, context: Context) {
        this.list.addAll(itemViewModelList)
    }

}