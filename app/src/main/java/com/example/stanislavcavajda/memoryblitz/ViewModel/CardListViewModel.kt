package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import android.os.Handler
import android.support.v4.content.ContextCompat
import com.example.stanislavcavajda.memoryblitz.BR
import com.example.stanislavcavajda.memoryblitz.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Created by stanislavcavajda on 26.9.17.
 */
class CardListViewModel : BaseObservable {

    var list = ObservableArrayList<CardViewModel>()
    var itemBinding : ItemBinding<CardViewModel> = ItemBinding.of(BR.viewModel, R.layout.card)
    var context: Context

    constructor(items: ArrayList<CardViewModel>, context: Context){
        list.addAll(items)
        this.context = context
    }
}