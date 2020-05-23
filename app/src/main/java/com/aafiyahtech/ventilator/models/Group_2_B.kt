package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_2_B(
    @SerializedName("MinuteVentilation") var minuteVentilation: Float,
    @SerializedName("ExpiratoryEndDelay") var expiratoryEndDelay: Int,
    @SerializedName("TerminationType") var terminationType: Int
){

    fun getTerminationTypeName(): String{

        return when(terminationType){
            1-> "End Inspiration "
            2-> "Tidal Volume "
            3-> "Flow"
            4-> "Time"
            else -> "$terminationType"
        }
    }

}