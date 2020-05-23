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
import com.aafiyahtech.ventilator.models.Group_3_B
import com.aafiyahtech.ventilator.models.MessageEvent
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_3_b.*
import org.greenrobot.eventbus.EventBus
import java.lang.Exception


class Group_3_B_Fragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_3_b, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        val group = model.mGroup3B
        group.observe(this, Observer {
            if (it != null ) {
                setDetails(it)
            }

        })

        apiCaller = ApiCaller(ApiCaller.GET_GROUP_3_B, model.appDataProvider!!.getIp())
        apiCaller.onApiResponseListener = onApiErrorListener
        apiCaller.mGroup3b = group
        apiCaller.start()

        btnUpdate.setOnClickListener {
            validateAndUpdate()
        }

    }

    private fun setDetails(group: Group_3_B) {
        etPlatePressure.setText("${group.platePressure}")
        etAcceleration.setText("${group.acceleration}")
        etInsOnDelay.setText("${group.inspiratoryONDelay}")
        etTPause.setText("${group.tPause}")
        etDummy1.setText("${group.dummy1}")
    }

    private fun validateAndUpdate() {

        val platePress = try {
            etPlatePressure.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (platePress == null){
            mfPlatePressure.error = "Invalid Value"
            return
        }
        mfPlatePressure.error = ""

        val insON = try {
            etInsOnDelay.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (insON == null) {
            mfInsOnDelay.error = "Invalid Value"
            return
        }
        mfInsOnDelay.error = ""

        val acceleration = try {
            etAcceleration.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (acceleration == null) {
            mfAcceleration.error = "Invalid value"
            return
        }
        mfAcceleration.error = ""

        val tPause = try {
            etTPause.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (tPause == null) {
            mfTPause.error = "Invalid value"
            return
        }
        mfTPause.error = ""

        val dummy1 = try {
            etDummy1.text.toString().toFloatOrNull()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        if (dummy1 == null) {
            mfDummy1.error = "Invalid value"
            return
        }
        mfDummy1.error = ""


        val group = Group_3_B(
            platePressure = platePress,
            acceleration = acceleration,
            inspiratoryONDelay = insON,
            tPause = tPause,
            dummy1 = dummy1
        )

        alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.PROGRESS_TYPE)
        alertDialog?.titleText = "Please wait!"
        alertDialog?.contentText = "Updating ventilator configuration."
        alertDialog?.setCancelable(false)
        alertDialog?.show()

        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_B, Gson().toJson(group))
    }



}
