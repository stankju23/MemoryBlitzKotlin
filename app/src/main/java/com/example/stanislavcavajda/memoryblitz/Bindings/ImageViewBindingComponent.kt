package com.example.stanislavcavajda.memoryblitz.Bindings

import android.databinding.DataBindingComponent

/**
 * Created by stanislavcavajda on 27.9.17.
 */

class ImageViewBindingComponent : DataBindingComponent {

    var mImageViewBinding = ImageViewBinding()

    override fun getImageViewBinding(): ImageViewBinding {
        return mImageViewBinding
    }

}