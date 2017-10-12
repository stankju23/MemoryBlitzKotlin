package com.example.stanislavcavajda.memoryblitz.Data

import com.example.stanislavcavajda.memoryblitz.ViewModel.CardViewModel
import com.example.stanislavcavajda.memoryblitz.Model.WantedCardModel

/**
 * Created by stanislavcavajda on 27.9.17.
 */
object DataManager {

    var graphicPacks = ArrayList<CardViewModel>()

    var selectedGraphicPack: String = ""

    var seconds = 0

    var typeSettingsActivity = 0

    var listProgressGameCards :ArrayList<Int> = arrayListOf(1,2,3,4,5,6,7,8,9)

    var numberOfCards: Int = 0

    var actualIndex: Int = 0

    var canClick: Boolean = false

    var cardMatrix: Int = 0

    var classicGameNumberOfCards: Int = 0

    var numberOfWantedCards: Int = 0

    var wantedCards: ArrayList<WantedCardModel> = ArrayList<WantedCardModel>()

}