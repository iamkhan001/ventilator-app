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
import com.aafiyahtech.ventilator.models.Group_5_B
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_5_b.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception


class Group_5_B_Fragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_5_b, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val group = model.mGroup5B
        group.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        apiCaller = ApiCaller(ApiCaller.GET_GROUP_5_B, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup5b = group
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }

    }

    private fun setDetails(group: Group_5_B) {
        etInsVel.setText("${group.insVel}")
        etExpVel.setText("${group.expVel}")
        etBreathTime.setText("${group.breathTime}")
    }

    private fun validateAndUpdate() {

        val insVel = try {
            etInsVel.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (insVel == null){
            mfInsVel.error = "Invalid Value"
            return
        }
        mfInsVel.error = ""

        val expVel = try {
            etExpVel.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (expVel == null) {
            mfExpVel.error = "Invalid Value"
            return
        }
        mfExpVel.error = ""

        val breathTime = try {
            etBreathTime.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (breathTime == null) {
            mfBreathTime.error = "Invalid value"
            return
        }
        mfBreathTime.error = ""


        val group = Group_5_B(
            insVel = insVel,
            expVel = expVel,
            breathTime = breathTime
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_5_B, Gson().toJson(group))
    }



}
