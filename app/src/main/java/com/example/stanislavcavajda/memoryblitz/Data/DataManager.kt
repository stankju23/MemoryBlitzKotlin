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

    var progressGameHighScore: Int = 0

    var actualScore: Int = 0

    var pauseMillis: Long = 0

    var actualCheckedGraphicPack: String = "scenery"

    var pointsList = ArrayList<PointViewModel>()

    var notificationsOn = false

    var notificationsTimeHour: Int = 0

    var notificationsTimeMinute: Int = 0

    var endGameElementPosition: Array<Float> = Array(2,{0.0f;0.0f})

    var translatedGraphicPacks = arrayListOf<String>()

    fun scoreMultiplicierClassicGame() :Double {
        var cardMatrixMultiplicier = 0.0
        var wantedCardsMulitplicier = 0.0
        when(cardMatrix) {
            Constants.CLASSIC_GAME_2x2 -> cardMatrixMultiplicier = 1.0
            Constants.CLASSIC_GAME_2x3 -> cardMatrixMultiplicier = 1.5
            Constants.CLASSIC_GAME_3x3 -> cardMatrixMultiplicier = 2.0
        }

        when(numberOfWantedCards) {
            Constants.WANTED_CARDS_ONE -> wantedCardsMulitplicier = 1.0
            Constants.WANTED_CARDS_TWO -> wantedCardsMulitplicier = 1.5
            Constants.WANTED_CARDS_THREE -> wantedCardsMulitplicier = 2.0
        }

        return  cardMatrixMultiplicier * wantedCardsMulitplicier
    }
    fun scoreMultiplicierProgressivGame() :Double {
        var scoreMultiplicier = 0.0
        if (numberOfCards < 5) {
            scoreMultiplicier = 1.0
        } else {
            when(numberOfCards) {
                6 -> scoreMultiplicier = 1.5
                7 -> scoreMultiplicier = 2.0
                8 -> scoreMultiplicier = 2.5
                9 -> scoreMultiplicier = 3.0

            }
        }
        return scoreMultiplicier
    }

}