package com.aafiyahtech.ventilator.utils

import android.content.Context
import android.widget.Toast

class MyMessage {


    companion object {

        fun showToast(context: Context, msg: String){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }

    }

}