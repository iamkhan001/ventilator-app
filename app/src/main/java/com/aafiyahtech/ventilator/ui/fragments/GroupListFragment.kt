package com.aafiyahtech.ventilator.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import androidx.recyclerview.widget.DividerItemDecoration
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.customViews.myDialog.SweetAlertDialog
import com.aafiyahtech.ventilator.models.*
import com.aafiyahtech.ventilator.ui.adapters.StatusAdapter
import kotlinx.android.synthetic.main.fragment_1_a.imgBack
import kotlinx.android.synthetic.main.fragment_status.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe


class GroupListFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var apiCaller: ApiCaller

    private val onApiErrorListener = object : ApiCaller.OnApiResponseListener{
        override fun onError(msg: String) {
            activity?.runOnUiThread {

                val alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                alertDialog.titleText = "Error"
                alertDialog.contentText = msg
                alertDialog.show()

            }
        }
    }

    companion object{
        private const val TAG = "GroupFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        rvList.addItemDecoration(
            DividerItemDecoration(
                context!!,
                DividerItemDecoration.VERTICAL
            )
        )

        val type = arguments!!.getString("type", ApiCaller.GET_GROUP_1_A)

        apiCaller = ApiCaller(type, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener

        when(type){
            ApiCaller.GET_GROUP_1_A->{
                val mGroup1A = model.mGroup1A
                mGroup1A.observe(this, Observer {

                    if (it != null ) {
                        showGroup1AList(it)
                    }

                })

                tvTitle.text = "Group 1 A"
                apiCaller.mGroup1a = mGroup1A
            }
            ApiCaller.GET_GROUP_1_B->{
                val mGroup1B = model.mGroup1B
                mGroup1B.observe(this, Observer {

                    if (it != null) {
                        showGroup1BList(it)
                    }

                })
                tvTitle.text = "Group 1 B"
                apiCaller.mGroup1b = mGroup1B
            }
            ApiCaller.GET_GROUP_2_A->{
                val mGroup2A = model.mGroup2A
                mGroup2A.observe(this, Observer {

                    if (it != null) {
                        showGroup2AList(it)
                    }

                })
                tvTitle.text = "Group 2 A"
                apiCaller.mGroup2a = mGroup2A
            }
            ApiCaller.GET_GROUP_2_B->{
                val mGroup2B = model.mGroup2B
                mGroup2B.observe(this, Observer {

                    if (it != null ) {
                        showGroup2BList(it)
                    }
                })
                tvTitle.text = "Group 2 B"
                apiCaller.mGroup2b = mGroup2B
            }
        }

        apiCaller.start()


        fabEdit.setOnClickListener {

            when(type) {

                ApiCaller.GET_GROUP_1_A -> {
                    val action = GroupListFragmentDirections.actionGroupListFragmentToGroup1AFragment()
                    view.findNavController().navigate(action)
                }
                ApiCaller.GET_GROUP_1_B -> {
                    val action = GroupListFragmentDirections.actionGroupListFragmentToGroup1BFragment()
                    view.findNavController().navigate(action)
                }
                ApiCaller.GET_GROUP_2_A -> {
                    val action = GroupListFragmentDirections.actionGroupListFragmentToGroup2AFragment()
                    view.findNavController().navigate(action)
                }
                ApiCaller.GET_GROUP_2_B -> {
                    val action = GroupListFragmentDirections.actionGroupListFragmentToGroup2BFragment()
                    view.findNavController().navigate(action)
                }

            }

        }

    }

    private fun showGroup1AList(item: Group_1_A) {

        val list = ArrayList<VentilatorStatus>()
        list.add(
            VentilatorStatus(getString(R.string.tidal_volume), "${item.tidalVolume} ${getString(R.string.unit_tidal_volume)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.peak_volume), "${item.peakVolume} ${getString(R.string.unit_peak_volume)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.pip), "${item.pip} ${getString(R.string.unit_pip)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.peep), "${item.peep} ${getString(R.string.unit_peep)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.pressure_support), "${item.pressureSupport} ${getString(R.string.unit_pressure_support)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.oxygen_rate), "${item.oxygenRate} ${getString(R.string.unit_oxygen_rate)}")
        )

        val adapter = StatusAdapter(list)
        rvList.adapter = adapter

    }


    private fun showGroup1BList(item: Group_1_B) {

        val list = ArrayList<VentilatorStatus>()
        list.add(
            VentilatorStatus(getString(R.string.respiratory_rate), "${item.respiratoryRate} ${getString(R.string.unit_respiratory_rate)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.inspirator_time), "${item.inspiratorTime} ${getString(R.string.unit_inspirator_time)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.inspiratory_end_delay), "${item.inspiratoryEndDelay} ${getString(R.string.unit_inspiratory_end_delay)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_type), "${item.triggerType} ${getString(R.string.unit_trigger_type)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_time), "${item.triggerTime} ${getString(R.string.unit_trigger_time)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_pressure), "${item.triggerPressure} ${getString(R.string.unit_trigger_pressure)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_flow), "${item.triggerFlow} ${getString(R.string.unit_trigger_flow)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.vent_mode), "${item.ventMode} ${getString(R.string.unit_vent_mode)}")
        )


        val adapter = StatusAdapter(list)
        rvList.adapter = adapter

    }

    private fun showGroup2AList(item: Group_2_A) {

        val list = ArrayList<VentilatorStatus>()
        list.add(
            VentilatorStatus(getString(R.string.inspiration_value), "${item.inspirationValue} ${getString(R.string.unit_inspiration_value)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.expiration_value), "${item.expirationValue} ${getString(R.string.unit_expiration_value)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.min_pressure), "${item.minPressure} ${getString(R.string.unit_min_pressure)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.max_pressure), "${item.maxPressure} ${getString(R.string.unit_max_pressure)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.min_air_flow), "${item.minAirFlow} ${getString(R.string.unit_min_air_flow)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.max_air_flow), "${item.maxAirFlow} ${getString(R.string.unit_max_air_flow)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.min_volume), "${item.minVolume} ${getString(R.string.unit_min_volume)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.max_volume), "${item.maxVolume} ${getString(R.string.unit_max_volume)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.min_oxygen), "${item.minOxygen} ${getString(R.string.unit_max_oxygen)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.max_oxygen), "${item.maxOxygen} ${getString(R.string.unit_min_oxygen)}")
        )

        val adapter = StatusAdapter(list)
        rvList.adapter = adapter

    }

    private fun showGroup2BList(item: Group_2_B) {

        val list = ArrayList<VentilatorStatus>()
        list.add(
            VentilatorStatus(getString(R.string.minute_ventilation), "${item.minuteVentilation} ${getString(R.string.unit_minute_ventilation)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.expiratory_end_delay), "${item.expiratoryEndDelay} ${getString(R.string.unit_expiratory_end_delay)}")
        )
        list.add(
            VentilatorStatus(getString(R.string.termination_type), "${item.terminationType} ${getString(R.string.unit_termination_type)}")
        )

        val adapter = StatusAdapter(list)
        rvList.adapter = adapter

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
            apiCaller.run()
        }
    }

}
