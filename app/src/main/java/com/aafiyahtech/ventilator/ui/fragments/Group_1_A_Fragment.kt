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
import com.aafiyahtech.ventilator.models.Group_1_A
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_1_a.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class Group_1_A_Fragment : Fragment() {


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
        private const val TAG = "1A"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val mGroup1a = model.mGroup1A
        mGroup1a.observe(this, Observer {

            if (it != null ) {
                setDetails(it)
            }

        })

        initViews()
        apiCaller = ApiCaller(ApiCaller.GET_GROUP_1_A, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup1a = mGroup1a
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }
    }

    private fun validateAndUpdate() {

        val tidalVol = try{
            etTidalVol.text.toString().toFloatOrNull()
        }catch (e: Exception){
            null
        }

        if (tidalVol == null || tidalVol !in tidalVolumeMin..tidalVolumeMax) {
            mfTidalVol.isErrorEnabled = true
            mfTidalVol.error = "Value must be in range"
            return
        }
        mfTidalVol.error = null
        mfTidalVol.isErrorEnabled = false

        val peakVol = try{
            etPeakVol.text.toString().toFloatOrNull()
        }catch (e: Exception){
            null
        }

        if (peakVol == null || peakVol !in peakVolumeMin..peakVolumeMax) {
            mfPeakVol.isErrorEnabled = true
            mfPeakVol.error = "Value must be in range"
            return
        }
        mfPeakVol.isErrorEnabled = false
        mfPeakVol.error = null

        val pip = try{
            etPip.text.toString().toFloatOrNull()
        }catch (e: Exception){
            null
        }

        if (pip == null || pip !in pipMin..pipMax) {
            mfPip.isErrorEnabled = true
            mfPip.error = "Value must be in range"
            return
        }
        mfPip.isErrorEnabled = false
        mfPip.error = null

        val peep = try{
            etPeep.text.toString().toIntOrNull()
        }catch (e: Exception){
            null
        }

        if (peep == null || peep !in peepMin..peepMax) {
            mfPeep.isErrorEnabled = true
            mfPeep.error = "Value must be in range"
            return
        }
        mfPeep.isErrorEnabled = false
        mfPeep.error = null

        val pressure = try{
            etPressure.text.toString().toFloatOrNull()
        }catch (e: Exception){
            null
        }

        if (pressure == null || pressure !in pressureSupportMin..pressureSupportMax) {
            mfPressure.isErrorEnabled = true
            mfPressure.error = "Value must be in range"
            return
        }
        mfPressure.isErrorEnabled = false
        mfPressure.error = null

        val oxy = try{
            etOxy.text.toString().toIntOrNull()
        }catch (e: Exception){
            null
        }

        if (oxy == null || oxy !in oxygenRateMin..oxygenRateMax) {
            mfOxy.isErrorEnabled = true
            mfOxy.error = "Value must be in range"
            return
        }
        mfOxy.isErrorEnabled = false
        mfOxy.error = null

        val g1a = Group_1_A(
            tidalVolume = tidalVol,
            peakVolume = peakVol,
            pip = pip,
            peep = peep,
            pressureSupport = pressure,
            oxygenRate = oxy
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_1_A, Gson().toJson(g1a))
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {

        tvRngTidalVol.text = "Range $tidalVolumeMin to $tidalVolumeMax"
        tvRngPeakVol.text = "Range $peakVolumeMin to $peakVolumeMax"
        tvRngPip.text = "Range $pipMin to $pipMax"
        tvRngPeep.text = "Range $peepMin to $peepMax"
        tvRngPressure.text = "Range $pressureSupportMin to $pressureSupportMax"
        tvRngOxy.text = "Range $oxygenRateMin to $oxygenRateMax"

        tvDefTidalVol.text = "Default $tidalVolumeDefault"
        tvDefPeakVol.text = "Default $peakVolumeDefault"
        tvDefPip.text = "Default $pipDefault"
        tvDefPeep.text = "Default $peepDefault"
        tvDefPressure.text = "Default $pressureSupportDefault"
        tvDefOxy.text = "Default $oxygenRateDefault"

        tvDefTidalVol.setOnClickListener {
            mfTidalVol.isErrorEnabled = false
            etTidalVol.setText("$tidalVolumeDefault")
        }
        tvDefPeakVol.setOnClickListener {
            mfPeakVol.isErrorEnabled = false
            etPeakVol.setText("$peakVolumeDefault")
        }
        tvDefPip.setOnClickListener {
            mfPip.isErrorEnabled = false
            etPip.setText("$pipDefault")
        }
        tvDefPeep.setOnClickListener {
            mfPeep.isErrorEnabled = false
            etPeep.setText("$peepDefault")
        }
        tvDefPressure.setOnClickListener {
            mfPressure.isErrorEnabled = false
            etPressure.setText("$pressureSupportDefault")
        }
        tvDefOxy.setOnClickListener {
            mfOxy.isErrorEnabled = false
            etOxy.setText("$oxygenRateDefault")
        }
    }

    private fun setDetails(group1A: Group_1_A) {

        etTidalVol.setText("${group1A.tidalVolume}")
        etPeakVol.setText("${group1A.peakVolume}")
        etPip.setText("${group1A.pip}")
        etPeep.setText("${group1A.peep}")
        etPressure.setText("${group1A.pressureSupport}")
        etOxy.setText("${group1A.oxygenRate}")

    }


}
