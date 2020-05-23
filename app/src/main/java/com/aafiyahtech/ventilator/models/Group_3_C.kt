package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_3_C(
    @SerializedName("CycleStart") val cycleStart: Int,
    @SerializedName("Homing") val homing: Int,
    @SerializedName("Dummy1") val dummy1: Int,
    @SerializedName("Dummy2") val dummy2: Int,
    @SerializedName("Dummy3") val dummy3: Int,
    @SerializedName("BuzzerOff") val buzzerOff: Int
)