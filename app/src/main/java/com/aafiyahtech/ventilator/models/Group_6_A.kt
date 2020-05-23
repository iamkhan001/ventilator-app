package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName


data class Group_6_A(
    @SerializedName("ActFlow") val actFlow: Float,
    @SerializedName("ActPressure") val actPressure: Float,
    @SerializedName("ActVolume") val actVol: Float,
    @SerializedName("RefTime") val refTime: Float
)