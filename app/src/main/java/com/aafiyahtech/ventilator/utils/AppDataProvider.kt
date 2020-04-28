package com.aafiyahtech.ventilator.utils

import android.content.Context

class AppDataProvider (context: Context) {


    companion object {

        private const val APP_PREF = "esp32"
        private var appDataProvider: AppDataProvider? = null

        fun getInstance(context: Context): AppDataProvider {

            if (appDataProvider == null) {
                appDataProvider =
                    AppDataProvider(context)
            }

            return appDataProvider!!
        }

    }


    private val preferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE)

    fun setIpAddress(ip: String) {
        val editor = preferences.edit()
        editor.putString("ip", ip)
        editor.apply()
    }

    fun getIp(): String = preferences.getString("ip","")!!

}