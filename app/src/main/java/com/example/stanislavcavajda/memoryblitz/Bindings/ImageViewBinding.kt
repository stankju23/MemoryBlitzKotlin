package com.example.stanislavcavajda.memoryblitz.Bindings

import android.databinding.BindingAdapter
import android.os.Handler
import android.widget.ImageView
import android.view.animation.AnimationUtils
import com.example.stanislavcavajda.memoryblitz.R


/**
 * Created by stanislavcavajda on 26.9.17.
 */

    @BindingAdapter("app:setImageSrc")
    fun setImageViewResource(view: ImageView, resId : Int) {
        view.setImageResource(resId)
    }

    @BindingAdapter("app:animation")
    fun setAnimation(view: ImageView, isAnimation: Boolean) {
        if (isAnimation) {
            var anim = AnimationUtils.loadAnimation(view.context, R.anim.zoom_in);
            view.startAnimation(anim)
        }
    }

    @BindingAdapter("app:backFlipp")
    fun setBackFlip(view: ImageView, isClicked: Boolean){
        if (isClicked) {
            var anim = AnimationUtils.loadAnimation(view.context, R.anim.zoom_in);
            view.startAnimation(anim)
        }
    }