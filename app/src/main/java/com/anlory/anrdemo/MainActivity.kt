package com.anlory.anrdemo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams
import com.anlory.anrdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private final var TAG = "ANRDemo"
    private lateinit var viewBindings: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBindings = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBindings.root)

        init()
        viewBindings.Input.setOnTouchListener { v, event -> inputAnr(event) }
        viewBindings.NoFocus.setOnClickListener { noFocusedWindowANR() }
        viewBindings.Service.setOnClickListener { serviceANR() }
        viewBindings.BroadCast.setOnClickListener { broadCastANR() }

    }

    private var BROADCAST_ACTION = "anr_demo"
    private fun init() {
        var intentFilter = IntentFilter()
        intentFilter.addAction(BROADCAST_ACTION)
        intentFilter.priority = 10
        class ANRBroadcastReceiver : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d(TAG, "broadcast receiver start!")
                sleep_s(15)
                Log.d(TAG, "broadcast receiver end !")
            }
        }
        registerReceiver(ANRBroadcastReceiver(), intentFilter)
    }

    private fun sleep_s(ss: Int) {
        try {
            Thread.sleep((ss * 1000).toLong())
        } catch (e: Exception) {
            Log.d(TAG, e.toString())
        }
    }

    private fun show(text: String) {
        viewBindings.show.text = text
    }

    var nfwPending = false
    private fun noFocusedWindowANR() {
        if (!nfwPending) {
            window.setFlags(LayoutParams.FLAG_NOT_FOCUSABLE, LayoutParams.FLAG_NOT_FOCUSABLE)
            show("NoFcousedWindowAnr in process \n Now Window is not focusable")
        } else {
            window.clearFlags(LayoutParams.FLAG_NOT_FOCUSABLE)
            show("NoFcousedWindowAnr in process \n Now Window is focusable")
        }
        nfwPending = !nfwPending
    }

    private fun serviceANR() {
        show("serviceANR")
        val intent = Intent(this, MyService::class.java)
//        startService(intent)
        startForegroundService(intent)
    }


    private fun broadCastANR() {
        show("broadCastANR")

        intent = Intent()
        intent.setAction(BROADCAST_ACTION)
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
        sendOrderedBroadcast(intent, null)
    }

    private fun inputAnr(event: MotionEvent): Boolean {
        show("InputAnr in process")
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            sleep_s(10)
        }
        return true
    }

}