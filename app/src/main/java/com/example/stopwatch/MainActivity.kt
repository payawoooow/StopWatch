package com.example.stopwatch

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    //valはReadOnly、varは変更可
    private var mTimer: Timer? = null
    private val mHandler = Handler()
    @RequiresApi(Build.VERSION_CODES.O) //なんやねん
    private var counter : LocalTime = LocalTime.MIN
    private var timerEnableFlg : Boolean = false
    lateinit var viewTimeCount : TextView //初期化を遅らせる
    lateinit var progress : ProgressBar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewTimeCount = findViewById(R.id.viewTimeCount)
        progress = findViewById(R.id.timeProgress)
        progress.visibility = View.GONE

        val btnStartAndStop : Button =  findViewById<Button>(R.id.buttonStartAndStop)
        btnStartAndStop.setOnClickListener {
            timerEnableFlg = !timerEnableFlg
            progress.visibility = if(timerEnableFlg) {
                View.VISIBLE
            }else{
                View.GONE
            }
        }

        val btnReset : Button = findViewById((R.id.buttonReset))
        btnReset.setOnClickListener{
            if(!timerEnableFlg){
                counter = LocalTime.MIN
                viewTimeCount.text = counter.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
            }
        }

    }

    override fun onResume() {
        super.onResume()

        mTimer = Timer()

        mTimer!!.schedule(object : TimerTask() {
            @RequiresApi(Build.VERSION_CODES.O) //なんやねん
            override fun run() {
                mHandler.post {
                    if(timerEnableFlg) {
                        counter = counter.plusSeconds(1)
                        viewTimeCount.text = counter.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    }
                }
            }
        }, 1000, 1000) //なんやねん
    }

    override fun onPause() {
        super.onPause()
        //タイマーの停止
        mTimer!!.cancel();
        mTimer = null;
    }
}