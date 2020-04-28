package com.aafiyahtech.ventilator.fragments


import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.dialogs.MyDialogFragment
import com.aafiyahtech.ventilator.utils.Repository
import kotlinx.android.synthetic.main.fragment_graphs.*

/**
 * A simple [Fragment] subclass.
 */
class GraphsFragment : Fragment() {


    private var isRunning = true
    private var repository: Repository? = null

    private val iResponse = object : Repository.IResponse {

        override fun onError(msg: String) {
           try {
               Log.e(tag, "onError: $msg")
               val myDialogFragment =
                   MyDialogFragment("Request Failed!", msg, "Retry", "Cancel", null)
               val ft = requireActivity().supportFragmentManager.beginTransaction()
               ft.show(myDialogFragment)
           }catch (e: Exception) {
               e.printStackTrace()
           }
        }

        override fun onGetGraph(actual_pos: Int, actual_vel: Int, cycle_time: Int) {

            val graphData = "Actual Pos: $actual_pos | actual Vel: $actual_vel | Cycle Time $cycle_time\n"

            tvGraph.append(graphData)

            Log.e("Graph","is running")
            if (isRunning) {
                Handler().postDelayed( {
                    repository?.getGraph(this)
                },1000)
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graphs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgBack.setOnClickListener {
            activity?.onBackPressed()
        }

        repository = Repository(requireContext())

        repository?.getGraph(iResponse)

    }

    override fun onDetach() {
        isRunning = false
        super.onDetach()

    }


}
