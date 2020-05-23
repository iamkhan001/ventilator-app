package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_2_B(
    @SerializedName("MinuteVentilation") var minuteVentilation: Float,
    @SerializedName("ExpiratoryEndDelay") var expiratoryEndDelay: Int,
    @SerializedName("TerminationType") var terminationType: Int
)