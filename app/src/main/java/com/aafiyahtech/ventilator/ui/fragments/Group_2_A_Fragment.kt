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
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.config.*
import com.aafiyahtech.ventilator.customViews.myDialog.SweetAlertDialog
import com.aafiyahtech.ventilator.models.Group_2_A
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_2_a.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class Group_2_A_Fragment : Fragment() {


    private val model: MainViewModel by activityViewModels()
    private lateinit var apiCaller: ApiCaller
    private var alertDialog: SweetAlertDialog? = null

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
        private const val TAG = "2A"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val mGroup2a = model.mGroup2A
        mGroup2a.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        initViews()
        apiCaller = ApiCaller(ApiCaller.GET_GROUP_2_A, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup2a = mGroup2a
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }
    }

    private fun validateAndUpdate() {

        val insVol = try{
            etInsVol.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (insVol == null || insVol !in inspirationValueMin..inspirationValueMax) {
            mfInsVol.isErrorEnabled = true
            mfInsVol.error = "Value must be in range"
            return
        }
        mfInsVol.error = null
        mfInsVol.isErrorEnabled = false

        val expVol = try{
            etExpVol.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (expVol == null || expVol !in expirationValueMin..expirationValueMax) {
            mfExpVol.isErrorEnabled = true
            mfExpVol.error = "Value must be in range"
            return
        }
        mfExpVol.isErrorEnabled = false
        mfExpVol.error = null

        val minPressure = try{
            etMinPressure.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (minPressure == null || minPressure !in minPressureMin..minPressureMax) {
            mfMinPressure.isErrorEnabled = true
            mfMinPressure.error = "Value must be in range"
            return
        }
        mfMinPressure.isErrorEnabled = false
        mfMinPressure.error = null

        val maxPressure = try{
            etMaxPressure.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (maxPressure == null || maxPressure !in maxPressureMin..maxPressureMax) {
            mfMaxPressure.isErrorEnabled = true
            mfMaxPressure.error = "Value must be in range"
            return
        }
        mfMaxPressure.isErrorEnabled = false
        mfMaxPressure.error = null

        val minAir = try{
            etMinAf.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
        if (minAir == null || minAir !in minAirFlowMin..minAirFlowMax) {
            mfMinAf.isErrorEnabled = true
            mfMinAf.error = "Value must be in range"
            return
        }
        mfMinAf.isErrorEnabled = false
        mfMinAf.error = null

        val maxAir = try{
            etMaxAf.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (maxAir == null || (maxAir !in maxAirFlowMin..maxAirFlowMax)) {
            mfMaxAf.isErrorEnabled = true
            mfMaxAf.error = "Value must be in range"
            return
        }
        mfMaxAf.isErrorEnabled = false
        mfMaxAf.error = null

        val minVol = try{
            etMinVol.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (minVol == null || (minVol !in minVolumeMin..minVolumeMax)) {
            mfMinVol.isErrorEnabled = true
            mfMinVol.error = "Value must be in range"
            return
        }
        mfMinVol.isErrorEnabled = false
        mfMinVol.error = null

        val maxVol = try{
            etMaxVol.text.toString().toFloatOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (maxVol == null || maxVol !in maxVolumeMin..maxVolumeMax) {
            mfMaxVol.isErrorEnabled = true
            mfMaxVol.error = "Value must be in range"
            return
        }
        mfMaxVol.isErrorEnabled = false
        mfMaxVol.error = null

        val minOxy = try{
            etMinOxy.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (minOxy == null || minOxy !in minOxygenMin..minOxygenMax) {
            mfMinOxy.isErrorEnabled = true
            mfMinOxy.error = "Value must be in range"
            return
        }
        mfMinOxy.isErrorEnabled = false
        mfMinOxy.error = null

        val maxOxy = try{
            etMaxOxy.text.toString().toIntOrNull()
        }catch (e: Exception){
            e.printStackTrace()
            null
        }

        if (maxOxy == null || maxOxy !in maxOxygenMin..maxOxygenMax) {
            mfMaxOxy.isErrorEnabled = true
            mfMaxOxy.error = "Value must be in range"
            return
        }
        mfMaxOxy.isErrorEnabled = false
        mfMaxOxy.error = null

        val g2a = Group_2_A(
            inspirationValue = insVol,
            expirationValue = expVol,
            minPressure = minPressure,
            maxPressure = maxPressure,
            minAirFlow = minAir,
            maxAirFlow = maxAir,
            minVolume = minVol,
            maxVolume = maxVol,
            minOxygen = minOxy,
            maxOxygen = maxOxy
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_2_A, Gson().toJson(g2a))
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {

        tvRngInsVol.text = "Range $inspirationValueMin to $inspirationValueMax"
        tvRngExpVol.text = "Range $expirationValueMin to $expirationValueMax"
        tvRngMinPressure.text = "Range $minPressureMin to $minPressureMax"
        tvRngMaxPressure.text = "Range $maxPressureMin to $maxPressureMax"
        tvRngMinAf.text = "Range $minAirFlowMin to $maxAirFlowMax"
        tvRngMaxAf.text = "Range $maxAirFlowMin to $maxAirFlowMax"
        tvRngMinVol.text = "Range $minVolumeMin to $minVolumeMax"
        tvRngMaxVol.text = "Range $maxVolumeMin to $maxVolumeMax"
        tvRngMinOxy.text = "Range $minOxygenMin to $minOxygenMax"
        tvRngMaxOxy.text = "Range $maxOxygenMin to $maxOxygenMax"

        tvDefInsVol.text = "Default $inspirationValueDefault"
        tvDefExpVol.text = "Default $expirationValueDefault"
        tvDefMinPressure.text = "Default $minPressureDefault"
        tvDefMaxPressure.text = "Default $maxPressureDefault"
        tvDefMinAf.text = "Default $minAirFlowDefault"
        tvDefMaxAf.text = "Default $maxAirFlowDefault"
        tvDefMinVol.text = "Default $minVolumeDefault"
        tvDefMaxVol.text = "Default $maxVolumeDefault"
        tvDefVMinOxy.text = "Default $minOxygenDefault"
        tvDefMaxOxy.text = "Default $maxOxygenDefault"

        tvDefInsVol.setOnClickListener {
            mfInsVol.isErrorEnabled = false
            etInsVol.setText("$inspirationValueDefault")
        }
        tvDefExpVol.setOnClickListener {
            mfExpVol.isErrorEnabled = false
            etExpVol.setText("$expirationValueDefault")
        }
        tvDefMinPressure.setOnClickListener {
            mfMinPressure.isErrorEnabled = false
            etMinPressure.setText("$minPressureDefault")
        }
        tvDefMaxPressure.setOnClickListener {
            mfMinVol.isErrorEnabled = false
            etMaxPressure.setText("$maxPressureDefault")
        }
        tvDefMinAf.setOnClickListener {
            mfMinAf.isErrorEnabled = false
            etMinAf.setText("$minAirFlowDefault")
        }
        tvDefMaxAf.setOnClickListener {
            mfMaxAf.isErrorEnabled = false
            etMaxAf.setText("$maxAirFlowDefault")
        }
        tvDefMinVol.setOnClickListener {
            mfMinVol.isErrorEnabled = false
            etMinVol.setText("$minVolumeDefault")
        }
        tvDefMaxVol.setOnClickListener {
            mfMaxVol.isErrorEnabled = false
            etMaxVol.setText("$maxVolumeDefault")
        }
        tvDefVMinOxy.setOnClickListener {
            mfMinOxy.isErrorEnabled = false
            etMinOxy.setText("$minOxygenDefault")
        }
        tvDefMaxOxy.setOnClickListener {
            mfMaxOxy.isErrorEnabled = false
            etMaxOxy.setText("$maxOxygenDefault")
        }
    }

    private fun setDetails(group: Group_2_A) {

        etInsVol.setText("${group.inspirationValue}")
        etExpVol.setText("${group.expirationValue}")
        etMinPressure.setText("${group.minPressure}")
        etMaxPressure.setText("${group.maxPressure}")
        etMinAf.setText("${group.minAirFlow}")
        etMaxAf.setText("${group.maxAirFlow}")
        etMinVol.setText("${group.minVolume}")
        etMaxVol.setText("${group.maxVolume}")
        etMinOxy.setText("${group.minOxygen}")
        etMaxOxy.setText("${group.maxOxygen}")

    }


}
