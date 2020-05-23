package com.aafiyahtech.ventilator.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aafiyahtech.ventilator.utils.AppDataProvider
import com.aafiyahtech.ventilator.R
import kotlinx.android.synthetic.main.activity_link.*

class LinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_link)

        val actionBar = supportActionBar
        actionBar?.title = "Link Est32"


        btnSave.setOnClickListener {

            val ip = etIP.text.toString()

            if (ip.length < 5) {
                etIP.error = "Enter Valid IP"
                return@setOnClickListener
            }

            AppDataProvider.getInstance(this).setIpAddress(ip)
            Toast.makeText(this, "IP Address Saved Successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        etIP.setText(AppDataProvider.getInstance(this).getIp())

    }
}
