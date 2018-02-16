package com.example.stanislavcavajda.memoryblitz

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_prepare.*

class PrepareActivity : AppCompatActivity() {


    var timer:CountDownTimer? = null
    var handler:Handler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare)

        handler = Handler()


        var time = findViewById<TextView>(R.id.time)
        time.setText(3.toString())

        timer = object :CountDownTimer(5000,1000) {

            override fun onTick(millis: Long) {
                if (millis/1000 == 1L) {
                    time.setText(0.toString())
                    var intent = Intent(this@PrepareActivity, ClassicGameActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_bottom)
                    finish()
                } else {
                    time.setText((millis/1000 - 1).toString())
                    time.animate().translationYBy( 20f).duration = 500
                    time.animate().scaleX(1.5f).duration = 500
                    time.animate().scaleY(1.5f).duration = 500
                    handler?.postDelayed(Runnable {
                        time.animate().translationYBy(- 20f).duration = 500
                        time.animate().scaleX(1f).duration = 500
                        time.animate().scaleY(1f).duration = 500
                    },500)
                }
            }

            override fun onFinish() {

            }

        }
        timer?.start()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        timer?.cancel()
    }

    override fun onStop() {
        super.onStop()
        timer?.cancel()
    }
}
