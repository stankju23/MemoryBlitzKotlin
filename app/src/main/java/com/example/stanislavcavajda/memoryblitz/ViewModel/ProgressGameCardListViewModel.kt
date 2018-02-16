package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList
import me.tatarka.bindingcollectionadapter2.ItemBinding
import com.example.stanislavcavajda.memoryblitz.BR
import com.example.stanislavcavajda.memoryblitz.R

/**
 * Created by stanislavcavajda on 02/11/2017.
 */
class ProgressGameCardListViewModel : BaseObservable {

    var list: ObservableArrayList<WantedCardModel> = ObservableArrayList()
    var itemBinding: ItemBinding<WantedCardModel> = ItemBinding.of(BR.viewModel,R.layout.mini_card)

    constructor(wantedCardsList: ArrayList<WantedCardModel>) {
        this.list.addAll(wantedCardsList)
    }
}