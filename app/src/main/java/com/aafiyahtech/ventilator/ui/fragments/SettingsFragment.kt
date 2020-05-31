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
import androidx.navigation.findNavController
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.activities.LinkActivity
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.aafiyahtech.ventilator.utils.AppDataProvider
import kotlinx.android.synthetic.main.fragment_settings.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SettingsFragment : Fragment(){

    companion object {
        const val minDataFetchInterval = 1
        const val minGraphUpdateInterval = 10
        const val minActTime = 10
        const val minGraphEntries = 1

        const val defActTimeInterval = 100

        const val maxDataFetchInterval = 15 - minDataFetchInterval
        const val maxGraphUpdateInterval = 99
        const val maxGraphEntries = 20 - minGraphEntries

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
        val actTime = appDataProvider.getActTime() / minActTime
        val graphUpdate = appDataProvider.getGraphUpdate() / minGraphUpdateInterval
        val graphEntries = appDataProvider.getGraphEntries() - minGraphEntries

        sbDataFetchInterval.progress = dataFetch
        sbGraphInterval.progress = graphUpdate
        sbGraphEntries.progress = graphEntries
        sbActualTimeInterval.progress = actTime

        val ip = appDataProvider.getIp()
        tvIp.text = ip

        tvDataFetchInterval.text = "${appDataProvider.getDataFetch()} Seconds"
        tvGraphUpdateInterval.text = "${appDataProvider.getGraphUpdate()} ms"
        tvActualTimeInterval.text = "${appDataProvider.getActTime()} ms"
        tvGraphEntries.text = "${appDataProvider.getGraphEntries()} Seconds"


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

                val p = (progress+1) * minGraphUpdateInterval

                tvGraphUpdateInterval.text = "$p ms"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Log.d(TAG, "Graph Update: "+seekBar?.progress)
                if (seekBar != null){
                    val p = (seekBar.progress+1) * minGraphUpdateInterval
                    appDataProvider.setGraphUpdate( p)
                }
            }

        })

        sbActualTimeInterval.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val p = (progress+1) * minActTime

                tvActualTimeInterval.text = "$p ms"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {


            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                Log.d(TAG, "Act Time: "+seekBar?.progress)
                if (seekBar != null){
                    val p = (seekBar.progress+1) * minActTime
                    appDataProvider.setActTime( p)
                }
            }

        })

        sbGraphEntries.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvGraphEntries.text = "${progress+ minGraphEntries} Seconds"
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


        setGraphRanges()

        tvChangeRange.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToGraphRangeFragment()
            view.findNavController().navigate(action)
        }
    }

    private fun setGraphRanges(){

        val text = "Actual Pressure: ${appDataProvider.getActPressureMin().toInt()} to ${appDataProvider.getActPressureMax().toInt()} \n"+
                "Actual Volume: ${appDataProvider.getActVolMin().toInt()} to ${appDataProvider.getActVolMax().toInt()} \n"+
                "Actual Flow: ${appDataProvider.getActFlowMin().toInt()} to ${appDataProvider.getActFlowMax().toInt()} \n"

        tvGraphRange.text = text
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {

        if(event == MessageEvent.UPDATE_CONFIG) {
            Log.e(TAG,"ON BACK")
            setGraphRanges()
        }
    }


}