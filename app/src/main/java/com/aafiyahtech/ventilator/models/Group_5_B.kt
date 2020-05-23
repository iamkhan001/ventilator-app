package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_5_B(
    @SerializedName("InspiVel") val insVel: Float,
    @SerializedName("ExpiVel") val expVel: Float,
    @SerializedName("BreathTime") val breathTime: Float
)