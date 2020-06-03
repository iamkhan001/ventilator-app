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
import com.aafiyahtech.ventilator.models.Group_2_B
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.aafiyahtech.ventilator.utils.MyMessage
import com.aafiyahtech.ventilator.utils.NumberUtils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_2_b.*
import org.greenrobot.eventbus.EventBus
import kotlin.Exception


class Group_2_B_Fragment : Fragment() {

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
        private const val TAG = "2B"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_2_b, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val mGroup2b = model.mGroup2B
        mGroup2b.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        initViews()
        apiCaller = ApiCaller(ApiCaller.GET_GROUP_2_B, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup2b = mGroup2b
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }

    }

    private fun setDetails(group: Group_2_B) {
        etMntVnt.setText("${group.minuteVentilation}")
        etExpEndDly.setText("${NumberUtils.toSeconds(group.expiratoryEndDelay)}")

    }

    private fun validateAndUpdate() {

        val minVent = try {
            etMntVnt.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (minVent == null || minVent !in minuteVentilationMin..minuteVentilationMax) {
            mfMntVnt.isErrorEnabled = true
            mfMntVnt.error = "Value must be in range"
            return
        }
        mfMntVnt.error = null
        mfMntVnt.isErrorEnabled = false

        var expEnd = try {
            etExpEndDly.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (expEnd == null || expEnd !in expiratoryEndDelayMin..expiratoryEndDelayMax) {
            mfExpEndDly.isErrorEnabled = true
            mfExpEndDly.error = "Value must be in range"
            return
        }
        mfExpEndDly.isErrorEnabled = false
        mfExpEndDly.error = null

        expEnd *= 1000
        val g2a = Group_2_B(
            minuteVentilation = minVent,
            expiratoryEndDelay = expEnd.toInt()
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_2_B, Gson().toJson(g2a))
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(){



        tvRngMntVnt.text = "Range $minuteVentilationMin to $minuteVentilationMax"
        tvRngExpEndDly.text = "Range $expiratoryEndDelayMin to $expiratoryEndDelayMax"

        tvDefMntVnt.text = "Default $minuteVentilationDefault"
        tvDefExpEndDly.text = "Default $expiratoryEndDelayDefault"

        tvDefMntVnt.setOnClickListener {
            mfMntVnt.isErrorEnabled = false
            etMntVnt.setText("$minuteVentilationDefault")
        }
        tvDefExpEndDly.setOnClickListener {
            mfExpEndDly.isErrorEnabled = false
            etExpEndDly.setText("$expiratoryEndDelayDefault")
        }

    }



}
