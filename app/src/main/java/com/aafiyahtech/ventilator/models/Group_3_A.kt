package com.aafiyahtech.ventilator.models

import com.google.gson.annotations.SerializedName

data class Group_3_A(
    @SerializedName("GraphType") val graphType: Float,
    @SerializedName("AppPollingRate") val appPoleRate: Float,
    @SerializedName("GraphPollingRate") val graphPolRate: Float
)