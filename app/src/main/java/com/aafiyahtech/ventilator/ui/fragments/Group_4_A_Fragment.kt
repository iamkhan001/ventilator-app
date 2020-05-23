package com.aafiyahtech.ventilator.ui.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.customViews.myDialog.SweetAlertDialog
import com.aafiyahtech.ventilator.models.Group_4_A
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_4_a.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception


class Group_4_A_Fragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_4_a, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val group = model.mGroup4A
        group.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        apiCaller = ApiCaller(ApiCaller.GET_GROUP_4_A, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup4a = group
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }

    }

    private fun setDetails(group: Group_4_A) {
        etOxyPercentage.setText("${group.oxyPercentage}")
        etInsFlow.setText("${group.insFlow}")
        etExpFlow.setText("${group.expFlow}")
        etInsPressure.setText("${group.insPressure}")
        etExpPressure.setText("${group.expPressure}")
        etActInsTime.setText("${group.actInsTime}")
        etActExpTime.setText("${group.actExpTime}")
        etActualBreath.setText("${group.actBreathTime}")
    }

    private fun validateAndUpdate() {

        val oxyPercentage = try {
            etOxyPercentage.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (oxyPercentage == null){
            mfOxyPercentage.error = "Invalid Value"
            return
        }
        mfOxyPercentage.error = ""

        val insFlow = try {
            etInsFlow.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (insFlow == null) {
            mfInsFlow.error = "Invalid Value"
            return
        }
        mfInsFlow.error = ""

        val insPressure = try {
            etInsPressure.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (insPressure == null) {
            mfInsPressure.error = "Invalid value"
            return
        }
        mfInsPressure.error = ""

        val extFlow = try {
            etExpFlow.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (extFlow == null) {
            mfExpFlow.error = "Invalid value"
            return
        }
        mfExpFlow.error = ""

        val expPressure = try {
            etExpPressure.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (expPressure == null) {
            mfExpPressure.error = "Invalid value"
            return
        }
        mfExpPressure.error = ""

        val insTime = try {
            etActInsTime.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (insTime == null) {
            mfActInsTime.error = "Invalid value"
            return
        }
        mfActInsTime.error = ""

        val expTime = try {
            etActExpTime.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (expTime == null) {
            mfActExpTime.error = "Invalid value"
            return
        }
        mfActExpTime.error = ""

        val actBreath = try {
            etActualBreath.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (actBreath == null) {
            mfActualBreath.error = "Invalid value"
            return
        }
        mfActualBreath.error = ""

        val group = Group_4_A(
            oxyPercentage = oxyPercentage,
            insFlow = insFlow,
            insPressure = insPressure,
            expFlow = extFlow,
            expPressure = expPressure,
            actInsTime = insTime,
            actExpTime = expTime,
            actBreathTime = actBreath
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_4_A, Gson().toJson(group))
    }



}
