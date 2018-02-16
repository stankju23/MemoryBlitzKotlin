package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.graphics.drawable.Drawable

/**
 * Created by stanislavcavajda on 11/10/2017.
 */
class WantedCardModel : BaseObservable  {
    var name:Int = 0
    var image:ObservableField<Drawable> = ObservableField()

    constructor(name: Int, image:Drawable) {
        this.name = name
        this.image.set(image)
    }
}
