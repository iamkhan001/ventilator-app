package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_3_B(
    @SerializedName("PlatePressure") val platePressure: Float,
    @SerializedName("Acceleration") val acceleration: Float,
    @SerializedName("InspiratoryONDelay") val inspiratoryONDelay: Int,
    @SerializedName("tPause") val tPause: Int,
    @SerializedName("dummy1") val dummy1: Float

)