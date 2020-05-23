package com.aafiyahtech.ventilator.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.koushikdutta.ion.Ion
import org.json.JSONObject

class Repository (private val context: Context) {

    companion object{
        private const val tag = "API"
    }

    private var url = "http://"+AppDataProvider.getInstance(context).getIp()

    fun getConfig(iResponse: IResponse) {

        val api = "$url?req=get_config"
        Log.e(tag, "api: $api")
        showMessage(api)
        Ion.with(context)
            .load(url)
            .addQuery("req","get_config")
            .asString()
            .setCallback { e, result ->

                Log.e(tag,"get config")
                if (e != null) {
                    Log.e(tag,"get config error")
                    iResponse.onError("Error Occurred!")
                    return@setCallback
                }

                Log.e(tag, result)

                try {

                    val obj = JSONObject(result)

                    if (obj.getBoolean("success")) {

                        val data  = obj.getJSONObject("config")

                        iResponse.onGetConfig(
                            data.getDouble("ie_ratio"),
                            data.getDouble("respiratory_rate"),
                            data.getDouble("tidal_vol"),
                            data.getInt("inspiratory_on"),
                            data.getInt("inspiratory_end"),
                            data.getInt("expiratory_on"),
                            data.getInt("expiratory_end")
                        )


                        return@setCallback
                    }

                    val msg = if (obj.has("message")) {
                        obj.getString("message")
                    }else {
                        "Cannot process request"
                    }

                    iResponse.onError(msg)

                }catch (e: Exception) {
                    e.printStackTrace()
                }

                iResponse.onError("Error Occurred!")
            }

    }

    fun setConfig(
       
        ieRatio: Double,
        respiratoryRate: Double,
        tidalVol: Double,
        inspiratoryOn: Int,
        inspiratoryEnd: Int,
        expiratoryOn: Int,
        expiratoryEnd: Int,
        iResponse: IResponse
    ) {
        val api = "$url?req=update_config&ie_ratio=$ieRatio&respiratory_rate=$respiratoryRate&tidal_vol=$tidalVol&inspiratory_on=$inspiratoryOn&inspiratory_end=$inspiratoryEnd&expiratory_on=$expiratoryOn&expiratory_end=$expiratoryEnd"
        Log.e(tag,api)

        Ion.with(context)
            .load(url)
            .addQuery("req","update_config")
            .addQuery("ie_ratio","$ieRatio")
            .addQuery("respiratory_rate","$respiratoryRate")
            .addQuery("tidal_vol","$tidalVol")
            .addQuery("inspiratory_on","$inspiratoryOn")
            .addQuery("inspiratory_end","$inspiratoryEnd")
            .addQuery("expiratory_on","$expiratoryOn")
            .addQuery("expiratory_end","$expiratoryEnd")
            .asString()
            .setCallback { e, result ->


                showMessage(api)

                if (e != null) {
                    iResponse.onError("Error Occurred!")
                    return@setCallback
                }

                Log.e(tag, result)

                try {

                    val obj = JSONObject(result)

                    if (obj.getBoolean("success")) {

                        iResponse.onUpdateConfig()

                        return@setCallback
                    }

                    val msg = if (obj.has("message")) {
                        obj.getString("message")
                    }else {
                        "Cannot process request"
                    }

                    iResponse.onError(msg)

                }catch (e: Exception) {
                    e.printStackTrace()
                }

                iResponse.onError("Error Occurred!")
            }

    }

    fun getStatus(iResponse: IResponse) {

        val api = "$url?req=get_status"
        Log.e(tag, "api: $api")
        showMessage(api)

        Ion.with(context)
            .load(url)
            .addQuery("req", "get_status")
            .asString()
            .setCallback { e, result ->

                if (e != null) {
                    iResponse.onError("Error Occurred!")
                    return@setCallback
                }

                Log.e(tag, result)

                try {

                    val obj = JSONObject(result)

                    if (obj.getBoolean("success")) {

                        val data  = obj.getJSONObject("status")

                        iResponse.onGetStatus(
                            data.getInt("mc_status"),
                            data.getInt("mc_error"),
                            data.getInt("cycle_time"),
                            data.getInt("breath_time"),
                            data.getInt("inspiration_time"),
                            data.getInt("inspiratory_time"),
                            data.getInt("expiratory_time"),
                            data.getInt("expiration_time"),
                            data.getInt("inspiratory_speed"),
                            data.getInt("expiratory_speed")
                        )


                        return@setCallback
                    }

                    val msg = if (obj.has("message")) {
                        obj.getString("message")
                    }else {
                        "Cannot process request"
                    }

                    iResponse.onError(msg)

                }catch (e: Exception) {
                    e.printStackTrace()
                }

                iResponse.onError("Error Occurred!")
            }

    }

    fun getGraph(iResponse: IResponse) {

        val api = "$url?req=get_graph"
        Log.e(tag, "api: $api")

        Ion.with(context)
            .load(url)
            .addQuery("req", "get_graph")
            .asString()
            .setCallback { e, result ->

                if (e != null) {
                    iResponse.onError("Error Occurred!")
                    return@setCallback
                }

                Log.e(tag, result)

                try {

                    val obj = JSONObject(result)

                    if (obj.getBoolean("success")) {

                        val data  = obj.getJSONObject("graph")

                        iResponse.onGetGraph(
                            data.getInt("actual_pos"),
                            data.getInt("actual_vel"),
                            data.getInt("cycle_time")
                        )


                        return@setCallback
                    }

                    val msg = if (obj.has("message")) {
                        obj.getString("message")
                    }else {
                        "Cannot process request"
                    }

                    iResponse.onError(msg)

                }catch (e: Exception) {
                    e.printStackTrace()
                }

                iResponse.onError("Error Occurred!")
            }

    }


    interface IResponse{


        fun onGetConfig(
            ieRatio: Double,
            respiratoryRate: Double,
            tidalVol: Double,
            inspiratoryOn: Int,
            inspiratoryEnd: Int,
            expiratoryOn: Int,
            expiratoryEnd: Int) {

        }

        fun onUpdateConfig() {

        }

        fun onGetStatus(
            mcStatus: Int,
            mcError: Int,
            cycleTime: Int,
            breathTime: Int,
            inspiratoryTime: Int,
            inspirationTime: Int,
            expiratoryTime: Int,
            expirationTime: Int,
            inspiratorySpeed: Int,
            expiratorySpeed: Int
            ) {

        }

        fun onGetGraph(
            actual_pos: Int,
            actual_vel: Int,
            cycle_time: Int
        ){

        }

        fun onError(msg: String)

    }

    private fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}