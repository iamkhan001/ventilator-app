package com.aafiyahtech.ventilator

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.koushikdutta.ion.Ion
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ip = AppDataProvider.getInstance(this).getIp()
        tvIp.text = "ESP32 IP Address: $ip"

        btnSend.setOnClickListener {
            val req = etAPI.text.toString().trim()
            sendRequest("http://$ip/$req")
        }

        tvChange.setOnClickListener {

            startActivity(Intent(this, LinkActivity::class.java))

        }
    }

    private fun sendRequest(req: String) {
        tvResponse.text = ""
        progressBar.visibility = View.VISIBLE

        Ion.with(this)
            .load(req)
            .asString()
            .setCallback { e, result ->

                progressBar.visibility = View.GONE

                if (e != null){
                    val error = "Error Occurred\n${e.message}"
                    tvResponse.text = error
                    return@setCallback
                }

                var response = "Request URL: $req\nResponse:\n"
                if (result != null) {
                    response += result
                }

                tvResponse.text = response

            }


    }
}
