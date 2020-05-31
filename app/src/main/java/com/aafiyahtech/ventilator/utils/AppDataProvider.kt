package com.aafiyahtech.ventilator.utils

import android.content.Context
import com.aafiyahtech.ventilator.ui.activities.GraphActivity
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

    fun getActTime(): Int = preferences.getInt("act_time",SettingsFragment.defActTimeInterval)

    fun getGraphUpdate(): Int = preferences.getInt("graph_updates",SettingsFragment.minGraphUpdateInterval * 5)

    fun getGraphEntries(): Int = preferences.getInt("graph_entries",SettingsFragment.minGraphEntries)

    fun getActPressureMin(): Float = preferences.getFloat("act_pres_min",GraphActivity.actPressureMin)
    fun getActPressureMax(): Float = preferences.getFloat("act_pres_max",GraphActivity.actPressureMax)

    fun getActFlowMin(): Float = preferences.getFloat("act_flow_min",GraphActivity.actFlowMin)
    fun getActFlowMax(): Float = preferences.getFloat("act_flow_max",GraphActivity.actFlowMax)

    fun getActVolMin(): Float = preferences.getFloat("act_vol_min",GraphActivity.actVolumeMin)
    fun getActVolMax(): Float = preferences.getFloat("act_vol_max",GraphActivity.actVolumeMax)


    fun setDataFetch(value: Int) {
        val editor = preferences.edit()
        editor.putInt("data_fetch", value)
        editor.apply()
    }

    fun setActTime(value: Int) {
        val editor = preferences.edit()
        editor.putInt("act_time", value)
        editor.apply()
    }

    fun setGraphUpdate(value: Int) {
        val editor = preferences.edit()
        editor.putInt("graph_updates", value)
        editor.apply()
    }

    fun setGraphEntries(value: Int) {
        val editor = preferences.edit()
        editor.putInt("graph_entries", value)
        editor.apply()
    }

    fun setActPressureMim(value: Float) {
        val editor = preferences.edit()
        editor.putFloat("act_pres_min", value)
        editor.apply()
    }

    fun setActPressureMax(value: Float) {
        val editor = preferences.edit()
        editor.putFloat("act_pres_max", value)
        editor.apply()
    }

    fun setActFlowMim(value: Float) {
        val editor = preferences.edit()
        editor.putFloat("act_flow_min", value)
        editor.apply()
    }

    fun setActFlowMax(value: Float) {
        val editor = preferences.edit()
        editor.putFloat("act_flow_max", value)
        editor.apply()
    }

    fun setActVolMin(value: Float) {
        val editor = preferences.edit()
        editor.putFloat("act_vol_min", value)
        editor.apply()
    }

    fun setActVolMax(value: Float) {
        val editor = preferences.edit()
        editor.putFloat("act_vol_max", value)
        editor.apply()
    }

}