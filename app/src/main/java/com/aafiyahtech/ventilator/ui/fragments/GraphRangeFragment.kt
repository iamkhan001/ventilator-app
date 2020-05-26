package com.aafiyahtech.ventilator.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.utils.AppDataProvider
import kotlinx.android.synthetic.main.fragment_graph_range.*

class GraphRangeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_range, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val appDataProvider = AppDataProvider.getInstance(requireContext())


        etActPressureMin.setText("${appDataProvider.getActFlowMin().toInt()}")
        etActPressureMax.setText("${appDataProvider.getActFlowMax().toInt()}")

        etActFlowMin.setText("${appDataProvider.getActFlowMin().toInt()}")
        etActFlowMax.setText("${appDataProvider.getActFlowMax().toInt()}")

        etActVolMin.setText("${appDataProvider.getActVolMin().toInt()}")
        etActVolMax.setText("${appDataProvider.getActVolMax().toInt()}")


        btnUpdate.setOnClickListener {

            val actPressureMin = etActPressureMin.text.toString().toFloatOrNull()
            val actPressureMax = etActPressureMax.text.toString().toFloatOrNull()

            if (actPressureMin == null || actPressureMax == null || actPressureMin >= actPressureMax){
                errorPressure.text = "Invalid Range"
                return@setOnClickListener
            }
            errorPressure.text = ""

            val actFlowMin = etActFlowMin.text.toString().toFloatOrNull()
            val actFlowMax = etActFlowMax.text.toString().toFloatOrNull()

            if (actFlowMin == null || actFlowMax == null || actFlowMin >= actFlowMax){
                errorFlow.text = "Invalid Range"
                return@setOnClickListener
            }
            errorFlow.text = ""

            val actVolMin = etActVolMin.text.toString().toFloatOrNull()
            val actVolMax = etActVolMax.text.toString().toFloatOrNull()

            if (actVolMin == null || actVolMax == null || actVolMin >= actVolMax){
                errorVol.text = "Invalid Range"
                return@setOnClickListener
            }
            errorVol.text = ""


            appDataProvider.setActPressureMim(actPressureMin)
            appDataProvider.setActPressureMax(actPressureMax)

            appDataProvider.setActFlowMim(actFlowMin)
            appDataProvider.setActFlowMax(actFlowMax)

            appDataProvider.setActVolMin(actVolMin)
            appDataProvider.setActVolMax(actVolMax)


            activity?.onBackPressed()

        }

    }



}