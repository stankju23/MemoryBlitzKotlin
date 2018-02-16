package com.example.stanislavcavajda.memoryblitz

import android.content.Context
import android.media.MediaPlayer

/**
 * Created by stanislavcavajda on 14/12/2017.
 */
class MusicController {

    private lateinit var mp: MediaPlayer
    private lateinit var context:Context
    constructor(context: Context) {
        this.context = context
        mp = MediaPlayer.create(context,R.raw.tap)
    }

    fun playSound() {
//        if (mp.isPlaying) {
//            mp.stop()
//            mp.release()
//        }
        mp.start()
    }
}