package com.aafiyahtech.ventilator.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aafiyahtech.ventilator.R
import com.aafiyahtech.ventilator.models.VentilatorStatus

class StatusAdapter(private val list: ArrayList<VentilatorStatus>): RecyclerView.Adapter<StatusAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_state, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item = list[position]

        holder.tvName.text = item.name
        holder.tvValue.text = item.value

    }


    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvValue: TextView = view.findViewById(R.id.tvValue)
    }

}