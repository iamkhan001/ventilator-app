package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_1_A(
    @SerializedName("TidalVolume") var tidalVolume: Float,
    @SerializedName("PeakVolume") var peakVolume: Float,
    @SerializedName("PIP") var pip: Float,
    @SerializedName("PEEP") var peep: Int,
    @SerializedName("PressureSupport") var pressureSupport: Float,
    @SerializedName("OxygenRate") var oxygenRate: Int)