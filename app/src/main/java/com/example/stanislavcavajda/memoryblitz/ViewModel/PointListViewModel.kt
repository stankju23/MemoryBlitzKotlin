package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import com.example.stanislavcavajda.memoryblitz.BR
import com.example.stanislavcavajda.memoryblitz.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Created by stanislavcavajda on 23/10/2017.
 */
class PointListViewModel : BaseObservable {

    var list: ObservableList<PointViewModel> = ObservableArrayList<PointViewModel>()
    var itemBinding : ItemBinding<PointViewModel> = ItemBinding.of(BR.viewModel, R.layout.point)

    constructor(pointList: ArrayList<PointViewModel>) {
        this.list.addAll(pointList)
    }
}