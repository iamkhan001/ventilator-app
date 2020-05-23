package com.aafiyahtech.ventilator.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.ui.activities.LinkActivity
import com.aafiyahtech.ventilator.utils.AppDataProvider
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment(){

    companion object {
        const val minDataFetchInterval = 1
        const val minGraphUpdateInterval = 1
        const val minGraphEntries = 10
        const val TAG = "Settings"
    }

    private lateinit var appDataProvider: AppDataProvider

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sbDataFetchInterval.max = 15
        sbGraphEntries.max = 15
        sbGraphInterval.max = 30

        appDataProvider = AppDataProvider.getInstance(requireContext())

        val ip = appDataProvider.getIp()
        tvIp.text = ip


        imgBack.setOnClickListener { activity?.onBackPressed() }

        sbDataFetchInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "Data Fetch: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }

        })

        sbGraphInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "Graph Update: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }

        })

        sbGraphEntries.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.e(TAG, "Graph Entries: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {


            }

        })

        tvChange.setOnClickListener {
            startActivity(Intent(requireContext(), LinkActivity::class.java))
        }
    }

}