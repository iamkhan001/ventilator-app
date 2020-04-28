package com.aafiyahtech.ventilator.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aafiyahtech.ventilator.R
import kotlinx.android.synthetic.main.dialog_my.*

class MyDialogFragment(private val title: String,
                       private val msg: String,
                       private val btnNamePositive: String,
                       private val btnNameNegative: String,
                       private val onDialogClickListener: OnDialogClickListener?) : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_my, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvTitle.text = title
        tvMessage.text = msg

        if (btnNamePositive.isNotEmpty()) {
            btnPositive.text = btnNamePositive
        }

        if (btnNameNegative.isNotEmpty()) {
            btnNegative.visibility = View.VISIBLE
            btnNegative.text = btnNameNegative
        }

        btnPositive.setOnClickListener {
            dismiss()
            onDialogClickListener?.onPositiveBtnClick()
        }

        btnNegative.setOnClickListener {
            dismiss()
            onDialogClickListener?.onNegativeBtnClick()
        }

    }


    interface OnDialogClickListener{

        fun onPositiveBtnClick() {

        }

        fun onNegativeBtnClick() {

        }

    }


}