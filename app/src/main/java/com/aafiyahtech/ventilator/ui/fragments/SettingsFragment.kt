package com.aafiyahtech.ventilator.ui.fragments

import android.annotation.SuppressLint
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

        const val maxDataFetchInterval = 15 - minDataFetchInterval
        const val maxGraphUpdateInterval = 15 - minGraphUpdateInterval
        const val maxGraphEntries = 30 - minGraphEntries

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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDataProvider = AppDataProvider.getInstance(requireContext())


        sbDataFetchInterval.max = maxDataFetchInterval
        sbGraphInterval.max = maxGraphUpdateInterval
        sbGraphEntries.max = maxGraphEntries



        val dataFetch = appDataProvider.getDataFetch() - minDataFetchInterval
        val graphUpdate = appDataProvider.getGraphUpdate() - minGraphUpdateInterval
        val graphEntries = appDataProvider.getGraphEntries() - minGraphEntries

        sbDataFetchInterval.progress = dataFetch
        sbGraphInterval.progress = graphUpdate
        sbGraphEntries.progress = graphEntries

        val ip = appDataProvider.getIp()
        tvIp.text = ip


        tvDataFetchInterval.text = "${appDataProvider.getDataFetch()} Seconds"
        tvGraphUpdateInterval.text = "${appDataProvider.getGraphUpdate()} Seconds"
        tvGraphEntries.text = "${appDataProvider.getGraphEntries()}"


        imgBack.setOnClickListener { activity?.onBackPressed() }

        sbDataFetchInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    tvDataFetchInterval.text = "${progress + minDataFetchInterval} Seconds"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Log.d(TAG, "Data Fetch: "+seekBar?.progress)
                if (seekBar != null){
                    appDataProvider.setDataFetch(seekBar.progress + minDataFetchInterval)
                }

            }

        })

        sbGraphInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvGraphUpdateInterval.text = "${progress+ minGraphUpdateInterval} Seconds"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Log.d(TAG, "Graph Update: "+seekBar?.progress)
                if (seekBar != null){
                    appDataProvider.setGraphUpdate(seekBar.progress + minGraphUpdateInterval)
                }
            }

        })

        sbGraphEntries.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvGraphEntries.text = "${progress+ minGraphEntries}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                Log.e(TAG, "Graph Entries: ${seekBar?.progress}")
                if (seekBar != null){
                    appDataProvider.setGraphEntries(seekBar.progress + minGraphEntries)
                }
            }

        })

        tvChange.setOnClickListener {
            startActivity(Intent(requireContext(), LinkActivity::class.java))
        }
    }

}