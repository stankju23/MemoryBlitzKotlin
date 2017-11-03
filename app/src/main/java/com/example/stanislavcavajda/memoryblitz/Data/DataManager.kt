package com.example.stanislavcavajda.memoryblitz.Data

import com.example.stanislavcavajda.memoryblitz.ViewModel.CardViewModel
import com.example.stanislavcavajda.memoryblitz.Model.WantedCardModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.GamePlanItemViewModel
import com.example.stanislavcavajda.memoryblitz.ViewModel.PointViewModel

/**
 * Created by stanislavcavajda on 27.9.17.
 */
object DataManager {

    var graphicPacksNames = arrayListOf<String>("christmas", "family", "scenery", "space", "summer", "toys", "western")

    var graphicPacks = ArrayList<CardViewModel>()

    var selectedGraphicPack: String = ""

    var timeToMemorize = 0

    var typeSettingsActivity = 0

    var listProgressGameCards :ArrayList<Int> = arrayListOf(1,2,3,4,5,6,7,8,9)

    var numberOfCards: Int = 0

    var actualIndex: Int = 0

    var canClick: Boolean = false

    var cardMatrix: Int = 0

    var classicGameNumberOfCards: Int = 0

    var numberOfWantedCards: Int = 0

    var wantedCards: ArrayList<WantedCardModel> = ArrayList<WantedCardModel>()

    var classicGameGamePlan: ArrayList<GamePlanItemViewModel> = ArrayList()

    var classicGameHighScore: Int = 0

    var actualScore: Int = 0

    var pauseMillis: Long = 0

    var actualCheckedGraphicPack: String = "scenery"

    var pointsList = ArrayList<PointViewModel>()

}