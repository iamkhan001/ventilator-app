package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_4_A(
    @SerializedName("OxygenPercentage") val oxyPercentage: Float,
    @SerializedName("InspiPressure") val insPressure: Float,
    @SerializedName("ExpiFlow") val expFlow: Float,
    @SerializedName("ExpiPressure") val expPressure: Float,
    @SerializedName("InspiFlow") val insFlow: Float,
    @SerializedName("ActBreathTime") val actBreathTime: Float,
    @SerializedName("ActInspiTime") val actInsTime: Float,
    @SerializedName("ActExpiTime") val actExpTime: Float
)