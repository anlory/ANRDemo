package com.anlory.anrdemo

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return  Binder()
//        TODO("Return the communication channel to the service.")
    }

    override fun onCreate() {
        try {
            Thread.sleep(23000)
        } catch (e: Exception) {
        }
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}