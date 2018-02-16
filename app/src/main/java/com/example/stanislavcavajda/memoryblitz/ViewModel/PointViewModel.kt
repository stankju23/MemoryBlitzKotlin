package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.databinding.BaseObservable
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt

class PointViewModel : BaseObservable {

    var correct: ObservableInt = ObservableInt()


    constructor(correct: Int){
        this.correct.set(correct)
    }
}