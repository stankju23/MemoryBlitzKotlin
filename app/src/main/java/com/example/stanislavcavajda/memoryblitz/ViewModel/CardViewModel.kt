package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import com.example.stanislavcavajda.memoryblitz.BR
import com.example.stanislavcavajda.memoryblitz.Model.Card
import com.example.stanislavcavajda.memoryblitz.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

/**
 * Created by stanislavcavajda on 26.9.17.
 */
class CardViewModel : BaseObservable {

    var list = ObservableArrayList<Card>()
    var itemBinding : ItemBinding<Card> = ItemBinding.of(BR.viewModel, R.layout.card)
    var context: Context

    constructor(items: ArrayList<Card>, context: Context){
        list.addAll(items)
        this.context = context
    }
}