package com.aafiyahtech.ventilator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (AppDataProvider.getInstance(this).getIp() == "") {
            startActivity(Intent(this, LinkActivity::class.java))
        }else {
            startActivity(Intent(this, MainActivity::class.java))
        }

        finish()
    }

}
