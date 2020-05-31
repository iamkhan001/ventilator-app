package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_1_B(
    @SerializedName("RespiratoryRate") var respiratoryRate: Int,
    @SerializedName("InspiratorTime") var inspiratorTime: Int,
    @SerializedName("InspiratoryEndDelay") var inspiratoryEndDelay: Int,
    @SerializedName("TriggerType") var triggerType: Int,
    @SerializedName("TriggerTime") var triggerTime: Int,
    @SerializedName("TriggerPressure") var triggerPressure: Float,
    @SerializedName("TriggerFlow") var triggerFlow: Float,
    @SerializedName("VentMode") var ventMode: Int,
    @SerializedName("TerminationType") var terminationType: Int
){

    fun getVentilatorModeName(): String{

        return when(ventMode) {
            1-> "PC-CMV"
            2-> "PC-SIMV"
            3-> "PC-PSV"
            4-> "VC-CMV"
            5-> "VC-SIMC"
            6-> "PRVC"
            7-> "CPAP"
            8-> "BiPAP"
            9-> "Ventbox1"
            10-> "Ventbox2"
            else -> "$ventMode"
        }

    }

    fun getTerminationTypeName(): String{

        return when(terminationType) {
            1-> "End Inspiration"
            2-> "Tidal Volume"
            3-> "Flow"
            4-> "Time"
            else -> "$terminationType"
        }

    }

    fun getTriggerTypeName(): String{

        return when(triggerType) {
            1-> "Time"
            2-> "Pressure"
            3-> "Flow"
            else -> "$triggerType"
        }

    }

}