package com.aafiyahtech.ventilator.utils

import android.content.Context
import com.aafiyahtech.ventilator.ui.fragments.SettingsFragment

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

    fun getDataFetch(): Int = preferences.getInt("data_fetch",SettingsFragment.minDataFetchInterval)

    fun getGraphUpdate(): Int = preferences.getInt("graph_update",SettingsFragment.minGraphUpdateInterval)

    fun getGraphEntries(): Int = preferences.getInt("graph_entries",SettingsFragment.minGraphEntries)


    fun setDataFetch(value: Int) {
        val editor = preferences.edit()
        editor.putInt("data_fetch", value)
        editor.apply()
    }

    fun setGraphUpdate(value: Int) {
        val editor = preferences.edit()
        editor.putInt("graph_update", value)
        editor.apply()
    }

    fun setGraphEntries(value: Int) {
        val editor = preferences.edit()
        editor.putInt("graph_entries", value)
        editor.apply()
    }

}