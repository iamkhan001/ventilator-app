package com.aafiyahtech.ventilator.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.aafiyahtech.ventilator.utils.AppDataProvider

class UpdaterService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var apiCaller: ApiCaller? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val type = intent!!.getStringExtra("type")!!

        apiCaller = ApiCaller(type, AppDataProvider.getInstance(this).getIp())
        apiCaller?.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        apiCaller?.stopExecution()
        super.onDestroy()
    }

}