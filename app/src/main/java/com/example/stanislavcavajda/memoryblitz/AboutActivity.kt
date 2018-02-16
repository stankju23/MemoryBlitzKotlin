package com.example.stanislavcavajda.memoryblitz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
    }
}
