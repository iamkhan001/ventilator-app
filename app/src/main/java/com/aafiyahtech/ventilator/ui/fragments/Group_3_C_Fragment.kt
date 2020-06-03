package com.aafiyahtech.ventilator.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.customViews.myDialog.SweetAlertDialog
import com.aafiyahtech.ventilator.models.Group_3_C
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_3_c.*
import org.json.JSONObject


class Group_3_C_Fragment : Fragment() {

    private val model: MainViewModel by activityViewModels()
    private lateinit var apiCaller: ApiCaller
    private var alertDialog: SweetAlertDialog? = null

    private var checkBtn: CompoundButton? = null
    private var update = false
    private val onApiErrorListener = object : ApiCaller.OnApiResponseListener{
        override fun onError(msg: String) {
            activity?.runOnUiThread {

                alertDialog?.dismissWithAnimation()
                alertDialog = SweetAlertDialog(requireContext(), SweetAlertDialog.ERROR_TYPE)
                alertDialog?.titleText = "Error"
                alertDialog?.contentText = msg
                alertDialog?.show()

                if (checkBtn != null) {
                    checkBtn!!.isChecked = !checkBtn!!.isChecked
                    checkBtn = null
                }

            }
        }

        override fun onSuccess() {
            activity?.runOnUiThread {
                if (checkBtn!=null){
                    Snackbar.make(checkBtn!!, "Configuration Updated Successfully!", Snackbar.LENGTH_SHORT).show()
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
        return inflater.inflate(R.layout.fragment_3_c, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            imgBack.setOnClickListener {
                activity?.onBackPressed()
            }

            val group = model.mGroup3C

            group.observe(this, Observer {
                setDetails(it)
            })

            apiCaller = ApiCaller(ApiCaller.GET_GROUP_3_C, model.appDataProvider!!.getIp())
            apiCaller.onApiResponseListener = onApiErrorListener
            apiCaller.mGroup3c = group


            btnCycleStart.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (!update){
                        return@setOnCheckedChangeListener
                    }

                    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    alertDialog.titleText = "Cycle Start"
                    alertDialog.contentText = "Do you want to update configuration?"
                    alertDialog.confirmText = "Yes"
                    alertDialog.cancelText = "No"
                    alertDialog.setConfirmClickListener {
                        alertDialog.dismissWithAnimation()
                        checkBtn = buttonView

                        val status = if (isChecked) {
                            1
                        } else {
                            0
                        }
                        val obj = JSONObject()
                        obj.put("CycleStart", status)
                        obj.put("Homing", 0)
                        obj.put("Reset", 0)
                        obj.put("FactoryReset", 0)
                        obj.put("Dummy3", 0)
                        obj.put("BuzzerOff", 0)

                        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_C, obj.toString())

                    }
                    alertDialog.setCancelClickListener {
                        update = false
                        buttonView.isChecked = !isChecked
                        it.dismissWithAnimation()
                        update = true
                    }
                    alertDialog.show()




                }
            }

            btnHoming.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (!update){
                        return@setOnCheckedChangeListener
                    }

                    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    alertDialog.titleText = "Homing"
                    alertDialog.contentText = "Do you want to update configuration?"
                    alertDialog.confirmText = "Yes"
                    alertDialog.cancelText = "No"
                    alertDialog.setConfirmClickListener {
                        alertDialog.dismissWithAnimation()
                        checkBtn = buttonView

                        val status = if (isChecked) {
                            1
                        } else {
                            0
                        }
                        val obj = JSONObject()
                        obj.put("CycleStart", 0)
                        obj.put("Homing", status)
                        obj.put("Reset", 0)
                        obj.put("FactoryReset", 0)
                        obj.put("Dummy3", 0)
                        obj.put("BuzzerOff", 0)

                        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_C, obj.toString())
                    }
                    alertDialog.setCancelClickListener {
                        update = false
                        buttonView.isChecked = !isChecked
                        it.dismissWithAnimation()
                        update = true
                    }
                    alertDialog.show()

                }


            }

            btnDummy1.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (!update){
                        return@setOnCheckedChangeListener
                    }

                    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    alertDialog.titleText = "Factory Reset"
                    alertDialog.contentText = "Do you want to update configuration?"
                    alertDialog.confirmText = "Yes"
                    alertDialog.cancelText = "No"
                    alertDialog.setConfirmClickListener {
                        alertDialog.dismissWithAnimation()
                        checkBtn = buttonView

                        val status = if (isChecked) {
                            1
                        } else {
                            0
                        }
                        val obj = JSONObject()
                        obj.put("CycleStart", 0)
                        obj.put("Homing", 0)
                        obj.put("Reset", status)
                        obj.put("FactoryReset", 0)
                        obj.put("Dummy3", 0)
                        obj.put("BuzzerOff", 0)

                        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_C, obj.toString())
                    }
                    alertDialog.setCancelClickListener {
                        update = false
                        buttonView.isChecked = !isChecked
                        it.dismissWithAnimation()
                        update = true
                    }
                    alertDialog.show()

                }
            }

            btnDummy2.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (!update){
                        return@setOnCheckedChangeListener
                    }
                    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    alertDialog.titleText = "Factory Reset"
                    alertDialog.contentText = "Do you want to update configuration?"
                    alertDialog.confirmText = "Yes"
                    alertDialog.cancelText = "No"
                    alertDialog.setConfirmClickListener {
                        alertDialog.dismissWithAnimation()
                        checkBtn = buttonView

                        val status = if (isChecked) {
                            1
                        } else {
                            0
                        }
                        val obj = JSONObject()
                        obj.put("CycleStart", 0)
                        obj.put("Homing", 0)
                        obj.put("Reset", 0)
                        obj.put("FactoryReset", status)
                        obj.put("Dummy3", 0)
                        obj.put("BuzzerOff", 0)

                        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_C, obj.toString())
                    }
                    alertDialog.setCancelClickListener {
                        update = false
                        buttonView.isChecked = !isChecked
                        it.dismissWithAnimation()
                        update = true
                    }
                    alertDialog.show()
                }
            }

            btnDummy3.setOnCheckedChangeListener { buttonView, isChecked ->
                run {
                    if (!update){
                        return@setOnCheckedChangeListener
                    }
                    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    alertDialog.titleText = "Dummy 3"
                    alertDialog.contentText = "Do you want to update configuration?"
                    alertDialog.confirmText = "Yes"
                    alertDialog.cancelText = "No"
                    alertDialog.setConfirmClickListener {
                        alertDialog.dismissWithAnimation()
                        checkBtn = buttonView

                        val status = if (isChecked) {
                            1
                        } else {
                            0
                        }
                        val obj = JSONObject()
                        obj.put("CycleStart", 0)
                        obj.put("Homing", 0)
                        obj.put("Reset", 0)
                        obj.put("FactoryReset", 0)
                        obj.put("Dummy3", status)
                        obj.put("BuzzerOff", 0)

                        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_C, obj.toString())
                    }
                    alertDialog.setCancelClickListener {
                        update = false
                        buttonView.isChecked = !isChecked
                        it.dismissWithAnimation()
                        update = true
                    }
                    alertDialog.show()
                }
            }

            btnBuzzerOff.setOnCheckedChangeListener { buttonView, isChecked ->
                run {

                    if (!update){
                        return@setOnCheckedChangeListener
                    }

                    val alertDialog = SweetAlertDialog(context, SweetAlertDialog.NORMAL_TYPE)
                    alertDialog.titleText = "Buzzer OFF"
                    alertDialog.contentText = "Do you want to update configuration?"
                    alertDialog.confirmText = "Yes"
                    alertDialog.cancelText = "No"
                    alertDialog.setConfirmClickListener {
                        alertDialog.dismissWithAnimation()
                        checkBtn = buttonView

                        val status = if (isChecked) {
                            1
                        } else {
                            0
                        }
                        val obj = JSONObject()
                        obj.put("CycleStart", 0)
                        obj.put("Homing", 0)
                        obj.put("Reset", 0)
                        obj.put("FactoryReset", 0)
                        obj.put("Dummy3", 0)
                        obj.put("BuzzerOff", status)

                        apiCaller.setDataGroup(ApiCaller.SET_GROUP_3_C, obj.toString())
                    }
                    alertDialog.setCancelClickListener {
                        update = false
                        buttonView.isChecked = !isChecked
                        it.dismissWithAnimation()
                        update = true
                    }
                    alertDialog.show()
                }
            }

        apiCaller.start()
    }

    private fun setDetails(it: Group_3_C) {

        update = false

        if (it.cycleStart == 1){
            btnCycleStart.isChecked = true
        }

        if (it.buzzerOff == 1) {
            btnBuzzerOff.isChecked = true
        }

        if (it.homing == 1){
            btnHoming.isChecked = true
        }

        if (it.dummy1 == 1){
            btnDummy1.isChecked = true
        }

        if (it.dummy2 == 1){
            btnDummy2.isChecked = true
        }

        if (it.dummy3 == 1){
            btnDummy3.isChecked = true
        }

        update = true

    }


}
