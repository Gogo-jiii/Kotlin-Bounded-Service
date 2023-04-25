package com.example.boundedservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.concurrent.Executors

class BoundedService : Service() {

    private val binder: IBinder = LocalBinder()
    var data = 0

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): BoundedService {
            computeData()
            return this@BoundedService
        }
    }

    private fun computeData() {
        val executorService = Executors.newSingleThreadExecutor()
        executorService.execute { //task
            for (i in 0..99) {
                data = i
                try {
                    Thread.sleep(5000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}