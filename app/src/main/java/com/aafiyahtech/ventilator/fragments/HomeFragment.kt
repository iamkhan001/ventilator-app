package com.aafiyahtech.ventilator.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.activities.LinkActivity
import com.aafiyahtech.ventilator.utils.AppDataProvider
import com.aafiyahtech.ventilator.viewModels.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ip = model.appDataProvider?.getIp()

        tvIp.text = ip

        btnConfig.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToConfigFragment()
            view.findNavController().navigate(action)
        }

        btnStatus.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToStatusFragment()
            view.findNavController().navigate(action)
        }

        btnGraph.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGraphsFragment()
            view.findNavController().navigate(action)
        }

        tvChange.setOnClickListener {
            startActivity(Intent(requireContext(), LinkActivity::class.java))
        }
    }

}
