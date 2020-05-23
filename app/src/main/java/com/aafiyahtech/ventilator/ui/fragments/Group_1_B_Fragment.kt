package com.aafiyahtech.ventilator.ui.fragments


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.config.*
import com.aafiyahtech.ventilator.customViews.myDialog.SweetAlertDialog
import com.aafiyahtech.ventilator.models.Group_1_B
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.aafiyahtech.ventilator.utils.MyMessage
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_1_b.*
import org.greenrobot.eventbus.EventBus
import kotlin.Exception

/**
 * A simple [Fragment] subclass.
 */
class Group_1_B_Fragment : Fragment() {


    private val model: MainViewModel by activityViewModels()
    private lateinit var apiCaller: ApiCaller
    private var alertDialog: SweetAlertDialog? = null

    private val ventModes = arrayOf("PC-CMV", "PC-SIMV", "PC-PSV", "VC-CMV", "VC-SIMC", "PRVC", "CPAP", "BiPAP")
    private var ventMode = 0

    private val onApiErrorListener = object : ApiCaller.OnApiResponseListener{
        override fun onError(msg: String) {
            activity?.runOnUiThread {

                alertDialog?.dismissWithAnimation()

                alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                alertDialog?.titleText = "Error"
                alertDialog?.contentText = msg
                alertDialog?.show()

            }
        }

        override fun onSuccess() {
            activity?.runOnUiThread {
                alertDialog?.changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                alertDialog?.titleText = "Success!"
                alertDialog?.contentText = "Configuration updated successfully"
                alertDialog?.setConfirmClickListener {
                    it.dismissWithAnimation()
                    activity?.onBackPressed()
                    EventBus.getDefault().post(MessageEvent.UPDATE_CONFIG)
                }
            }
        }
    }

    companion object{
        private const val TAG = "1B"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val mGroup1b = model.mGroup1B
        mGroup1b.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        initViews()
        apiCaller = ApiCaller(ApiCaller.GET_GROUP_1_B, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup1b = mGroup1b
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }
    }

    private fun validateAndUpdate() {

        val rspRate = try{
            etRspRate.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (rspRate == null || rspRate !in respiratoryRateMin..respiratoryRateMax) {
            mfRspRate.isErrorEnabled = true
            mfRspRate.error = "Value must be in range"
            return
        }
        mfRspRate.error = null
        mfRspRate.isErrorEnabled = false

        val insTime = try{
            etInsTime.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (insTime == null || insTime !in inspiratorTimeMin..inspiratorTimeMax) {
            mfInsTime.isErrorEnabled = true
            mfInsTime.error = "Value must be in range"
            return
        }
        mfInsTime.isErrorEnabled = false
        mfInsTime.error = null

        val endTime = try{
            etInsEndTime.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (endTime == null || endTime !in inspiratoryEndDelayMin..inspiratoryEndDelayMax) {
            mfInsEndTime.isErrorEnabled = true
            mfInsEndTime.error = "Value must be in range"
            return
        }
        mfInsEndTime.isErrorEnabled = false
        mfInsEndTime.error = null

        val triggerType = try{
            etTriggerType.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (triggerType == null || triggerType !in triggerTypeMin..triggerTypeMax) {
            mfTriggerType.isErrorEnabled = true
            mfTriggerType.error = "Value must be in range"
            return
        }
        mfTriggerType.isErrorEnabled = false
        mfTriggerType.error = null

        val triggerTime = try{
            etTriggerTime.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
        if (triggerTime == null || triggerTime !in triggerTimeMin..triggerTimeMax) {
            mfTriggerTime.isErrorEnabled = true
            mfTriggerTime.error = "Value must be in range"
            return
        }
        mfTriggerTime.isErrorEnabled = false
        mfTriggerTime.error = null

        val pressure = try{
            etTriggerPressure.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (pressure == null || (pressure !in triggerPressureMin..triggerPressureMax)) {
            mfTriggerPressure.isErrorEnabled = true
            mfTriggerPressure.error = "Value must be in range"
            return
        }
        mfTriggerPressure.isErrorEnabled = false
        mfTriggerPressure.error = null

        val triggerFlow = try{
            etTriggerFlow.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (triggerFlow == null || (triggerFlow !in triggerFlowMin..triggerFlowMax)) {
            mfTriggerFlow.isErrorEnabled = true
            mfTriggerFlow.error = "Value must be in range"
            return
        }
        mfTriggerFlow.isErrorEnabled = false
        mfTriggerFlow.error = null


        val g1b = Group_1_B(
            respiratoryRate = rspRate,
            inspiratorTime = insTime,
            inspiratoryEndDelay = endTime,
            triggerType = triggerType,
            triggerTime = triggerTime,
            triggerPressure = pressure,
            triggerFlow = triggerFlow,
            ventMode = ventMode
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_1_B, Gson().toJson(g1b))
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {

        val adapter = ArrayAdapter<String>(requireContext(), R.layout.item_text, ventModes)
        spnVentMode.adapter = adapter

        spnVentMode.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                ventMode = 1
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                ventMode = position+1
            }

        }

        tvRngRspRate.text = "Range $respiratoryRateMin to $respiratoryRateMax"
        tvRngInsTime.text = "Range $inspiratorTimeMin to $inspiratorTimeMax"
        tvRngInsEndTime.text = "Range $inspiratoryEndDelayMin to $inspiratoryEndDelayMax"
        tvRngTriggerFlow.text = "Range $triggerFlowMin to $triggerFlowMax"
        tvRngTriggerPressure.text = "Range $triggerPressureMin to $triggerPressureMax"
        tvRngTriggerTime.text = "Range $triggerTimeMin to $triggerTimeMax"
        tvRngTriggerType.text = "Range $triggerTypeMin to $triggerTypeMax"

        tvDefRspRate.text = "Default $respiratoryRateDefault"
        tvDefInsTime.text = "Default $inspiratorTimeDefault"
        tvDefInsEndTime.text = "Default $inspiratoryEndDelayDefault"
        tvDefTriggerFlow.text = "Default $triggerFlowDefault"
        tvDefTriggerPressure.text = "Default $triggerPressureDefault"
        tvDefTriggerTime.text = "Default $triggerTimeDefault"
        tvDefTriggerType.text = "Default $triggerTypeDefault"

        tvDefRspRate.setOnClickListener {
            mfRspRate.isErrorEnabled = false
            etRspRate.setText("$respiratoryRateDefault")
        }
        tvDefInsTime.setOnClickListener {
            mfInsTime.isErrorEnabled = false
            etInsTime.setText("$inspiratorTimeDefault")
        }
        tvDefInsEndTime.setOnClickListener {
            mfInsEndTime.isErrorEnabled = false
            etInsEndTime.setText("$inspiratoryEndDelayDefault")
        }
        tvDefTriggerFlow.setOnClickListener {
            mfTriggerFlow.isErrorEnabled = false
            etTriggerFlow.setText("$triggerFlowDefault")
        }
        tvDefTriggerPressure.setOnClickListener {
            mfTriggerPressure.isErrorEnabled = false
            etTriggerPressure.setText("$triggerPressureDefault")
        }
        tvDefTriggerTime.setOnClickListener {
            mfTriggerTime.isErrorEnabled = false
            etTriggerTime.setText("$triggerTimeDefault")
        }
        tvDefTriggerType.setOnClickListener {
            mfTriggerType.isErrorEnabled = false
            etTriggerType.setText("$triggerTypeDefault")
        }

    }

    private fun setDetails(group: Group_1_B) {

        etRspRate.setText("${group.respiratoryRate}")
        etInsTime.setText("${group.inspiratorTime}")
        etInsEndTime.setText("${group.inspiratoryEndDelay}")
        etTriggerFlow.setText("${group.triggerFlow}")
        etTriggerPressure.setText("${group.triggerPressure}")
        etTriggerTime.setText("${group.triggerTime}")
        etTriggerType.setText("${group.triggerType}")
        try {
            if(group.ventMode in 1..ventModeMax){
                spnVentMode.setSelection(group.ventMode-1)
            }else {
                MyMessage.showToast(requireContext(),"Invalid Vent Mode")
            }
        }catch (e: Exception){
            spnVentMode.setSelection(0)
        }


    }


}
