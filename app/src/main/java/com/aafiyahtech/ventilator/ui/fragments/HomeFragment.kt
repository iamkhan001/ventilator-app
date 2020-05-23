package com.aafiyahtech.ventilator.ui.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.ui.activities.GraphActivity
import com.aafiyahtech.ventilator.ui.activities.HomeActivity
import com.aafiyahtech.ventilator.ui.viewModels.MainViewModel
import com.aafiyahtech.ventilator.utils.ApiCaller
import kotlinx.android.synthetic.main.fragment_main.*

class HomeFragment : Fragment() {

    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ip = model.appDataProvider?.getIp()

        tvIp.text = ip

        imgMenu.setOnClickListener {
            (context as HomeActivity).toggleMenu()
        }

        btnGrp1a.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroupListFragment(ApiCaller.GET_GROUP_1_A)
            view.findNavController().navigate(action)
        }

        btnGrp1b.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroupListFragment(ApiCaller.GET_GROUP_1_B)
            view.findNavController().navigate(action)
        }

        btnGrp2a.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroupListFragment(ApiCaller.GET_GROUP_2_A)
            view.findNavController().navigate(action)
        }
        btnGrp2b.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroupListFragment(ApiCaller.GET_GROUP_2_B)
            view.findNavController().navigate(action)
        }

        btn3a.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroup3AFragment()
            view.findNavController().navigate(action)
        }
        btn3b.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroup3BFragment()
            view.findNavController().navigate(action)
        }
        btn4a.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroupListFragment(ApiCaller.GET_GROUP_2_B)
            view.findNavController().navigate(action)
        }
        btn5b.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToGroup5BFragment()
            view.findNavController().navigate(action)
        }
        btn6a.setOnClickListener {
            startActivity(Intent(requireContext(), GraphActivity::class.java))
        }


    }

}
