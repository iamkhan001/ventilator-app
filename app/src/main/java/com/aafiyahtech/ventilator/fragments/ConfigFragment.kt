package com.aafiyahtech.ventilator.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.dialogs.MyDialogFragment
import com.aafiyahtech.ventilator.utils.Repository
import com.aafiyahtech.ventilator.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_config.*

class ConfigFragment : Fragment() {


    private val iResponse = object : Repository.IResponse {

        override fun onError(msg: String) {
            requireActivity().runOnUiThread {
                Log.e(tag,"onError: $msg")
                progressBar.visibility = View.GONE
                val myDialogFragment = MyDialogFragment("Request Failed!", msg, "Retry", "Cancel", null)
                val ft = requireActivity().supportFragmentManager.beginTransaction()
                ft.show(myDialogFragment)
            }


        }

        override fun onGetConfig(
            ieRatio: Double,
            respiratoryRate: Double,
            tidalVol: Double,
            inspiratoryOn: Int,
            inspiratoryEnd: Int,
            expiratoryOn: Int,
            expiratoryEnd: Int
        ) {

            progressBar.visibility = View.GONE

            etIeRatio.setText("$ieRatio")
            etRspRate.setText("$respiratoryRate")
            etTidalVol.setText("$tidalVol")
            etInsOn.setText("$inspiratoryOn")
            etInsEnd.setText("$inspiratoryEnd")
            etExpOn.setText("$expiratoryOn")
            etExpEnd.setText("$expiratoryEnd")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_config, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val model: MainViewModel by activityViewModels()

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }


        loadConfig()
    }


    private fun loadConfig() {

        progressBar.visibility = View.VISIBLE

        val repository = Repository(requireContext())

        repository.getConfig(iResponse)


    }


}
