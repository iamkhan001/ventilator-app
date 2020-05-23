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
    @SerializedName("VentMode") var ventMode: Int
)