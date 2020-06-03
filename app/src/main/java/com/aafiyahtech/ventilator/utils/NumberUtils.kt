package com.aafiyahtech.ventilator.utils

import android.util.Log
import java.text.DecimalFormat

class NumberUtils {

    companion object {

        fun formatFloat(number: Float): Float {
            val decimalFormat = DecimalFormat("#.##")
            val n = decimalFormat.format(number)
            Log.e("Format", "n= $n")
            return n.toFloat()
        }

        fun toSeconds(value: Int): Float {
            return value.toFloat() / 1000
        }

        fun toSeconds(value: Float): Float {
            return value / 1000
        }
    }

}