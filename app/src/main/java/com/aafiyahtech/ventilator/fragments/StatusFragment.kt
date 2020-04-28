package com.aafiyahtech.ventilator.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aafiyahtech.ventilator.R
import androidx.recyclerview.widget.DividerItemDecoration
import com.aafiyahtech.ventilator.adapters.StatusAdapter
import com.aafiyahtech.ventilator.dialogs.MyDialogFragment
import com.aafiyahtech.ventilator.models.VentilatorStatus
import com.aafiyahtech.ventilator.utils.Repository
import kotlinx.android.synthetic.main.fragment_status.*


class StatusFragment : Fragment() {

    private val iResponse = object : Repository.IResponse {

        override fun onError(msg: String) {
            requireActivity().runOnUiThread {
                Log.e(tag, "onError: $msg")
                progressBar.visibility = View.GONE
                val myDialogFragment =
                    MyDialogFragment("Request Failed!", msg, "Retry", "Cancel", null)
                val ft = requireActivity().supportFragmentManager.beginTransaction()
                ft.show(myDialogFragment)
            }
        }

        override fun onGetStatus(
            mcStatus: Int,
            mcError: Int,
            cycleTime: Int,
            breathTime: Int,
            inspiratoryTime: Int,
            inspirationTime: Int,
            expiratoryTime: Int,
            expirationTime: Int,
            inspiratorySpeed: Int,
            expiratorySpeed: Int
        ) {

            progressBar.visibility = View.GONE

            val list = ArrayList<VentilatorStatus>()
            list.add(VentilatorStatus("McStatusCode", mcStatus))
            list.add(VentilatorStatus("McErrorCode", mcError))
            list.add(VentilatorStatus("Cycle Time", cycleTime))
            list.add(VentilatorStatus("Breath Time", breathTime))
            list.add(VentilatorStatus("Inspiratory Time", inspiratoryTime))
            list.add(VentilatorStatus("Inspiration Time", inspirationTime))
            list.add(VentilatorStatus("Expiratory Time", expiratoryTime))
            list.add(VentilatorStatus("Expiration Time", expirationTime))
            list.add(VentilatorStatus("Inspiratory Speed", inspiratorySpeed))
            list.add(VentilatorStatus("Expiratory Spped", expiratorySpeed))

            val adapter = StatusAdapter(list)
            rvList.adapter = adapter

        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        rvList.addItemDecoration(
            DividerItemDecoration(
                activity!!,
                DividerItemDecoration.VERTICAL
            )
        )


        loadStatus()

    }

    private fun loadStatus() {

        progressBar.visibility = View.VISIBLE

        val repository = Repository(requireContext())
        repository.getStatus(iResponse)


    }


}
