package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.app.Activity
import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.stanislavcavajda.memoryblitz.Data.Constants
import com.example.stanislavcavajda.memoryblitz.Data.DataManager
import com.example.stanislavcavajda.memoryblitz.R
import kotlinx.android.synthetic.main.activity_speed_game.view.*

/**
 * Created by stanislavcavajda on 26.9.17.
 */
class CardViewModel : BaseObservable{

    var image = ObservableField<Drawable>()
    var context: Context
    var checked = false
    var title = ObservableField<String>()
    lateinit var recyclerView: RecyclerView
    var name: String = ""

    constructor(name: String, context: Context,checked: Boolean,title: String){
        this.name = name
        this.context = context
        var id = context.resources.getIdentifier(this.name,"drawable",context.packageName)
        this.image.set(ContextCompat.getDrawable(context,id))
        this.title.set(title)
        this.checked = checked
    }

    fun check(v:View) {

        when(DataManager.typeSettingsActivity) {

            Constants.PROGRESS_GAME -> recyclerView = (context as Activity).findViewById<RecyclerView>(R.id.endless_recycler_view)
            Constants.CLASSIC_GAME -> recyclerView = (context as Activity).findViewById<RecyclerView>(R.id.recyclerView)
        }

        for (item in DataManager.graphicPacks) {
            item.checked = false
        }
        name = name.substring(0,name.count() - 2)
        DataManager.actualCheckedGraphicPack = name

        recyclerView.adapter.notifyDataSetChanged()

        checked = true
        recyclerView.adapter.notifyDataSetChanged()
        DataManager.selectedGraphicPack = name
    }
}