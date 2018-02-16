package com.example.stanislavcavajda.memoryblitz.ViewModel

import android.databinding.BaseObservable
import android.databinding.ObservableField

/**
 * Created by stanislavcavajda on 11/10/2017.
 */
class TimerViewModel:BaseObservable {

    var timer: ObservableField<String> = ObservableField<String>()
    var mainText: ObservableField<String> = ObservableField<String>()
    var actualScore: ObservableField<String> = ObservableField<String>()


    constructor(timer:String, mainText: String, actualScore:String){
        this.timer.set(timer)
        this.mainText.set(mainText)
        this.actualScore.set(actualScore)
    }
}