package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_3_A(
    @SerializedName("GraphType") val graphType: Int,
    @SerializedName("AppPollingRate") val appPoleRate: Int,
    @SerializedName("GraphPollingRate") val graphPolRate: Int
)