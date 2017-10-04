package com.example.stanislavcavajda.memoryblitz.Bindings

import android.databinding.BindingAdapter
import android.widget.ImageView

/**
 * Created by stanislavcavajda on 26.9.17.
 */
class ImageViewBinding () {

    @BindingAdapter("app:setImageSrc")
    fun setImageViewResource(view: ImageView, resId : Int) {
        view.setImageResource(resId)
    }
}