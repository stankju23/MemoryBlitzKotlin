package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.databinding.BaseObservable
import android.databinding.ObservableArrayList


/**
 * Created by stanislavcavajda on 11/10/2017.
 */
class WantedCardsListViewModel : BaseObservable {

    var list: ObservableArrayList<WantedCardModel> = ObservableArrayList<WantedCardModel>()

    constructor(cardList: ArrayList<WantedCardModel>) {
        this.list.addAll(cardList)
    }

}