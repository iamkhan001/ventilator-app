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
import com.aafiyahtech.ventilator.models.Group_3_A
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_3_a.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception


class Group_3_A_Fragment : Fragment() {

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
        private const val TAG = "3A"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_3_a, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        initView()

        val mGroup3a = model.mGroup3A
        mGroup3a.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        apiCaller = ApiCaller(ApiCaller.GET_GROUP_3_A, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup3a = mGroup3a
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun initView() {

        tvRngGraphType.text = "Range $minGraphType to $maxGraphType"
        tvRngAppPollingRate.text = "Range $minAppPollingRate to $maxAppPollingRate"
        tvRngGraphPollingRate.text = "Range $minGraphPollingRate to $maxGraphPollingRate"

        tvDefGraphType.text = "Default $defGraphType"
        tvDefAppPollingRate.text = "Default $defAppPollingRate"
        tvDefGraphPollingRate.text = "Default $defGraphPollingRate"

        tvDefGraphPollingRate.setOnClickListener {
            etGraphPollRate.setText("$defGraphPollingRate")
            mfGraphPollRate.error = ""
            mfGraphPollRate.isErrorEnabled = false
        }

        tvDefAppPollingRate.setOnClickListener {
            etAppPollRate.setText("$defAppPollingRate")
            mfAppPollRate.error = ""
            mfAppPollRate.isErrorEnabled = false
        }

        tvDefGraphType.setOnClickListener {
            etGraphType.setText("$defGraphType")
            mfGraphType.error = ""
            mfGraphType.isErrorEnabled = false
        }

    }

    private fun setDetails(group: Group_3_A) {
        etGraphType.setText("${group.graphType}")
        etAppPollRate.setText("${group.appPoleRate}")
        etGraphPollRate.setText("${group.graphPolRate}")
    }

    private fun validateAndUpdate() {

        val graphType = try {
            etGraphType.text.toString().toIntOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (graphType == null || graphType !in minGraphType..maxGraphType) {
            mfGraphType.isErrorEnabled = true
            mfGraphType.error = "Invalid Value"
            return
        }
        mfGraphType.error = ""
        mfGraphType.isErrorEnabled = false

        val appPoll = try {
            etAppPollRate.text.toString().toIntOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (appPoll == null || appPoll !in minAppPollingRate..maxAppPollingRate) {
            mfAppPollRate.isErrorEnabled = true
            mfAppPollRate.error = "Invalid Value"
            return
        }
        mfAppPollRate.error = ""
        mfAppPollRate.isErrorEnabled = false

        val graphPoll = try {
            etGraphPollRate.text.toString().toIntOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (graphPoll == null || graphPoll !in minGraphPollingRate..maxGraphPollingRate) {
            mfGraphType.isErrorEnabled = true
            mfGraphPollRate.error = "Invalid value"
            return
        }
        mfGraphPollRate.error = ""
        mfGraphType.isErrorEnabled = false


        val group = Group_3_A(
            graphType = graphType,
            appPoleRate = appPoll,
            graphPolRate = graphPoll
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_A, Gson().toJson(group))
    }



}
