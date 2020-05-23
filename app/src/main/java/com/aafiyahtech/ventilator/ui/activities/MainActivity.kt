package com.aafiyahtech.ventilator.ui.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import org.greenrobot.eventbus.EventBus

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val model: MainViewModel by viewModels()
        model.init(this)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        EventBus.getDefault().post(MessageEvent.UPDATE_CONFIG)
    }


}
