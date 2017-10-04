package com.example.stanislavcavajda.memoryblitz.Data

import android.support.v4.content.ContextCompat
import com.example.stanislavcavajda.memoryblitz.Model.Card

/**
 * Created by stanislavcavajda on 27.9.17.
 */
object DataManager {

    var graphicPacks = ArrayList<Card>()

    var selectedGraphicPack: String = ""

    var seconds = 0

    var typeSettingsActivity = 0

    var listProgressGameCards :ArrayList<Int> = arrayListOf(1,2,3,4,5,6,7,8,9)

    var numberOfCards: Int = 0

    var actualIndex: Int = 0

    var canClick: Boolean = false
}