package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_2_A(
    @SerializedName("InspirationValue") var inspirationValue: Float,
    @SerializedName("ExpirationValue") var expirationValue: Float,
    @SerializedName("MinPressure") var minPressure: Int,
    @SerializedName("MaxPressure") var maxPressure: Int,
    @SerializedName("MinAirFlow") var minAirFlow: Float,
    @SerializedName("MaxAirFlow") var maxAirFlow: Float,
    @SerializedName("MinVolume") var minVolume: Float,
    @SerializedName("MaxVolume") var maxVolume: Float,
    @SerializedName("MinOxygen") var minOxygen: Int,
    @SerializedName("MaxOxygen") var maxOxygen: Int
)