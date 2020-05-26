package com.aafiyahtech.ventilator.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
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
import com.aafiyahtech.ventilator.utils.AppDataProvider
import kotlinx.android.synthetic.main.fragment_1_a.imgBack
import kotlinx.android.synthetic.main.fragment_status.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe


class GroupListFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var apiCaller: ApiCaller

    private lateinit var adapter: StatusAdapter
    private val list = ArrayList<VentilatorStatus>()

    private val onApiErrorListener = object : ApiCaller.OnApiResponseListener{
        override fun onError(msg: String) {
            if (isStopped){
                return
            }
            activity?.runOnUiThread {

                val alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                alertDialog.titleText = "Error"
                alertDialog.contentText = msg
                alertDialog.show()

            }
        }
    }

    private var isStopped = false
    private var isReady = true
    private var showUpdates = true

    companion object{
        private const val TAG = "GroupFragment"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e(TAG,"ON ATTACH")

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
            showUpdates = false
            isStopped = true
            activity?.onBackPressed()
        }

        rvList.addItemDecoration(
            DividerItemDecoration(
                context!!,
                DividerItemDecoration.VERTICAL
            )
        )

        isReady = true
        showUpdates = true
        isStopped = false

        val type = arguments!!.getString("type", ApiCaller.GET_GROUP_1_A)
        val appDataProvider = AppDataProvider.getInstance(requireContext())
        apiCaller = ApiCaller(type, appDataProvider.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        val delay = (appDataProvider.getDataFetch() * 1000).toLong()
        Log.e(TAG, "delay $delay")
        apiCaller.delay = delay
        apiCaller.isRecursive = true


        when(type){
            ApiCaller.GET_GROUP_1_A->{
                val mGroup1A = model.mGroup1A
                mGroup1A.observe(this, Observer {

                    Log.e(TAG,"observe 1A: $showUpdates $isReady")
                    if (showUpdates && isReady) {
                        showGroup1AList(it)
                    }

                })

                tvTitle.text = "Group 1 A"
                apiCaller.mGroup1a = mGroup1A
            }
            ApiCaller.GET_GROUP_1_B->{
                val mGroup1B = model.mGroup1B
                mGroup1B.observe(this, Observer {

                    if (showUpdates && isReady) {
                        showGroup1BList(it)
                    }

                })
                tvTitle.text = "Group 1 B"
                apiCaller.mGroup1b = mGroup1B
            }
            ApiCaller.GET_GROUP_2_A->{
                val mGroup2A = model.mGroup2A
                mGroup2A.observe(this, Observer {

                    if (showUpdates && isReady) {
                        showGroup2AList(it)
                    }

                })
                tvTitle.text = "Group 2 A"
                apiCaller.mGroup2a = mGroup2A
            }
            ApiCaller.GET_GROUP_2_B->{
                val mGroup2B = model.mGroup2B
                mGroup2B.observe(this, Observer {

                    if (showUpdates && isReady) {
                        showGroup2BList(it)
                    }
                })
                tvTitle.text = "Group 2 B"
                apiCaller.mGroup2b = mGroup2B
            }
            ApiCaller.GET_GROUP_3_A -> {
                val mGroup = model.mGroup3A
                mGroup.observe(this, Observer {

                    if (showUpdates && isReady) {
                        showGroup3AList(it)
                    }
                })
                tvTitle.text = "Group 3 A"
                apiCaller.mGroup3a = mGroup
            }
            ApiCaller.GET_GROUP_3_B -> {
                val mGroup = model.mGroup3B
                mGroup.observe(this, Observer {
                    if (showUpdates && isReady) {
                        showGroup3BList(it)
                    }
                })
                tvTitle.text = "Group 3 B"
                apiCaller.mGroup3b = mGroup
            }
            ApiCaller.GET_GROUP_4_A -> {
                val mGroup = model.mGroup4A
                mGroup.observe(this, Observer {
                    if (showUpdates && isReady) {
                        showGroup4AList(it)
                    }
                })
                tvTitle.text = "Group 4 A"
                apiCaller.mGroup4a = mGroup
            }
            ApiCaller.GET_GROUP_5_B -> {
                val mGroup = model.mGroup5B
                mGroup.observe(this, Observer {
                    if (showUpdates && isReady) {
                        showGroup5BList(it)
                    }
                })
                tvTitle.text = "Group 5 B"
                apiCaller.mGroup5b = mGroup
            }
        }

        if (type == ApiCaller.GET_GROUP_4_A || type == ApiCaller.GET_GROUP_5_B || type == ApiCaller.GET_GROUP_6_A) {
            fabEdit.hide()
        }


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
                ApiCaller.GET_GROUP_3_A -> {
                    val action = GroupListFragmentDirections.actionGroupListFragmentToGroup3AFragment()
                    view.findNavController().navigate(action)
                }
                ApiCaller.GET_GROUP_3_B -> {
                    val action = GroupListFragmentDirections.actionGroupListFragmentToGroup3BFragment()
                    view.findNavController().navigate(action)
                }
            }
            showUpdates = false
            isStopped = true
            apiCaller.stopExecution()

        }

        adapter = StatusAdapter(list)
        rvList.adapter = adapter

        Handler().post(apiCaller)
    }

    private fun showGroup1AList(item: Group_1_A) {
        isReady = false

        list.clear()
        list.add(
            VentilatorStatus(getString(R.string.tidal_volume), "${item.tidalVolume}", getString(R.string.unit_tidal_volume))
        )
        list.add(
            VentilatorStatus(getString(R.string.peak_volume), "${item.peakVolume}", getString(R.string.unit_peak_volume))
        )
        list.add(
            VentilatorStatus(getString(R.string.pip), "${item.pip}", getString(R.string.unit_pip))
        )
        list.add(
            VentilatorStatus(getString(R.string.peep), "${item.peep}", getString(R.string.unit_peep))
        )
        list.add(
            VentilatorStatus(getString(R.string.pressure_support), "${item.pressureSupport}", getString(R.string.unit_pressure_support))
        )
        list.add(
            VentilatorStatus(getString(R.string.oxygen_rate), "${item.oxygenRate}", getString(R.string.unit_oxygen_rate))
        )

        adapter.notifyDataSetChanged()
        isReady = true

    }


    private fun showGroup1BList(item: Group_1_B) {
        isReady = false

        list.clear()

        list.add(
            VentilatorStatus(getString(R.string.vent_mode), item.getVentilatorModeName(), getString(R.string.unit_vent_mode))
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_type), item.getTriggerTypeName(), getString(R.string.unit_trigger_type))
        )
        list.add(
            VentilatorStatus(getString(R.string.termination_type), item.getTerminationTypeName(), getString(R.string.unit_termination_type))
        )
        list.add(
            VentilatorStatus(getString(R.string.respiratory_rate), "${item.respiratoryRate}", getString(R.string.unit_respiratory_rate))
        )
        list.add(
            VentilatorStatus(getString(R.string.inspirator_time), "${item.inspiratorTime}", getString(R.string.unit_inspirator_time))
        )
        list.add(
            VentilatorStatus(getString(R.string.inspiratory_end_delay), "${item.inspiratoryEndDelay}", getString(R.string.unit_inspiratory_end_delay))
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_type), "${item.triggerType}", getString(R.string.unit_trigger_type))
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_time), "${item.triggerTime}", getString(R.string.unit_trigger_time))
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_pressure), "${item.triggerPressure}", getString(R.string.unit_trigger_pressure))
        )
        list.add(
            VentilatorStatus(getString(R.string.trigger_flow), "${item.triggerFlow}", getString(R.string.unit_trigger_flow))
        )

        adapter.notifyDataSetChanged()
        isReady = true


    }

    private fun showGroup2AList(item: Group_2_A) {
        isReady = false

        list.clear()
        list.add(
            VentilatorStatus(getString(R.string.inspiration_value), "${item.inspirationValue}", getString(R.string.unit_inspiration_value))
        )
        list.add(
            VentilatorStatus(getString(R.string.expiration_value), "${item.expirationValue}", getString(R.string.unit_expiration_value))
        )
        list.add(
            VentilatorStatus(getString(R.string.min_pressure), "${item.minPressure}", getString(R.string.unit_min_pressure))
        )
        list.add(
            VentilatorStatus(getString(R.string.max_pressure), "${item.maxPressure}", getString(R.string.unit_max_pressure))
        )
        list.add(
            VentilatorStatus(getString(R.string.min_air_flow), "${item.minAirFlow}", getString(R.string.unit_min_air_flow))
        )
        list.add(
            VentilatorStatus(getString(R.string.max_air_flow), "${item.maxAirFlow}", getString(R.string.unit_max_air_flow))
        )
        list.add(
            VentilatorStatus(getString(R.string.min_volume), "${item.minVolume}", getString(R.string.unit_min_volume))
        )
        list.add(
            VentilatorStatus(getString(R.string.max_volume), "${item.maxVolume}", getString(R.string.unit_max_volume))
        )
        list.add(
            VentilatorStatus(getString(R.string.min_oxygen), "${item.minOxygen}", getString(R.string.unit_max_oxygen))
        )
        list.add(
            VentilatorStatus(getString(R.string.max_oxygen), "${item.maxOxygen}", getString(R.string.unit_min_oxygen))
        )

        adapter.notifyDataSetChanged()

        isReady = true

    }

    private fun showGroup2BList(item: Group_2_B) {
        isReady = false

        list.clear()
        list.add(
            VentilatorStatus(getString(R.string.minute_ventilation), "${item.minuteVentilation}", getString(R.string.unit_minute_ventilation))
        )
        list.add(
            VentilatorStatus(getString(R.string.expiratory_end_delay), "${item.expiratoryEndDelay}", getString(R.string.unit_expiratory_end_delay))
        )

        adapter.notifyDataSetChanged()

        isReady = true

    }

    private fun showGroup3AList(item: Group_3_A) {
        isReady = false

        list.clear()

        list.add(
            VentilatorStatus(getString(R.string.graph_type), "${item.graphType}", getString(R.string.unit_graph_type))
        )
        list.add(
            VentilatorStatus(getString(R.string.app_polling_rate), "${item.appPoleRate}", getString(R.string.unit_app_polling_rate))
        )
        list.add(
            VentilatorStatus(getString(R.string.graph_polling_rate), "${item.graphPolRate}", getString(R.string.unit_graph_polling_rate))
        )

        adapter.notifyDataSetChanged()
        isReady = true


    }

    private fun showGroup3BList(item: Group_3_B) {

        isReady = false

        list.clear()

        list.add(
            VentilatorStatus(getString(R.string.plate_pressure), "${item.platePressure}", getString(R.string.unit_plate_pressure))
        )
        list.add(
            VentilatorStatus(getString(R.string.acceleration), "${item.acceleration}", getString(R.string.unit_acceleration))
        )
        list.add(
            VentilatorStatus(getString(R.string.inspiratory_on_delay), "${item.inspiratoryONDelay}", getString(R.string.unit_inspiratory_on_delay))
        )
        list.add(
            VentilatorStatus(getString(R.string.t_pause), "${item.tPause}", getString(R.string.unit_t_pause))
        )
        list.add(
            VentilatorStatus(getString(R.string.dummy1), "${item.dummy1}", getString(R.string.dummy1))
        )

        adapter.notifyDataSetChanged()

        isReady = true

    }

    private fun showGroup4AList(item: Group_4_A) {
        isReady = false

        list.clear()

        list.add(
            VentilatorStatus(getString(R.string.oxygen_sensor), "${item.oxyPercentage}", getString(R.string.unit_oxygen_sensor))
        )
        list.add(
            VentilatorStatus(getString(R.string.inspiratory_pressure_sensor), "${item.insPressure}", getString(R.string.unit_inspiratory_pressure_sensor))
        )
        list.add(
            VentilatorStatus(getString(R.string.inspiratory_flow_sensor), "${item.insFlow}", getString(R.string.unit_inspiratory_flow_sensor))
        )
        list.add(
            VentilatorStatus(getString(R.string.expiratory_pressure_sensor), "${item.expPressure}", getString(R.string.unit_expiratory_pressure_sensor))
        )
        list.add(
            VentilatorStatus(getString(R.string.expiratory_flow_sensor), "${item.expFlow}", getString(R.string.unit_expiratory_flow_sensor))
        )
        list.add(
            VentilatorStatus(getString(R.string.actual_inspiratory_time), "${item.actInsTime}", getString(R.string.unit_actual_inspiratory_time))
        )
        list.add(
            VentilatorStatus(getString(R.string.actual_expiratory_time), "${item.actExpTime}", getString(R.string.unit_actual_expiratory_time))
        )
        list.add(
            VentilatorStatus(getString(R.string.actual_breath_time), "${item.actBreathTime}", getString(R.string.unit_actual_breath_time))
        )
        adapter.notifyDataSetChanged()

        isReady = true

    }

    private fun showGroup5BList(item: Group_5_B) {
        isReady = false
        list.clear()

        list.add(
            VentilatorStatus(getString(R.string.inspiration_velocity), "${item.insVel}", getString(R.string.unit_inspiration_velocity))
        )
        list.add(
            VentilatorStatus(getString(R.string.expiration_velocity), "${item.expVel}", getString(R.string.unit_expiration_velocity))
        )
        list.add(
            VentilatorStatus(getString(R.string.breath_time), "${item.breathTime}", getString(R.string.unit_breath_time))
        )

        adapter.notifyDataSetChanged()

        isReady = true
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
            showUpdates = true
            isStopped = false
            isReady = true
            apiCaller.run()
        }
    }

    override fun onDetach() {
        Log.e(TAG,"ON DETACH")
        apiCaller.stopExecution()
        super.onDetach()
    }

}
