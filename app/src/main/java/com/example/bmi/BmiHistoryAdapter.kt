package com.example.bmi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BmiHistoryAdapter(val bmiDatas: ArrayList<String>) : RecyclerView.Adapter<BmiHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.bmi_data_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.firstData.text = bmiDatas[position]
    }

    override fun getItemCount() = bmiDatas.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstData: TextView = itemView.findViewById(R.id.firstBmiData)
    }
}