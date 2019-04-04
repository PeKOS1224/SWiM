package com.example.bmi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bmi.Logic.BmiEntriesQueue
import com.example.bmi.Logic.BmiHistoryEntry
import java.text.DateFormat

class BmiHistoryAdapter(bmiEntries: BmiEntriesQueue) : RecyclerView.Adapter<BmiHistoryAdapter.ViewHolder>() {

    private val entriesSet:ArrayList<BmiHistoryEntry> = bmiEntries.asArrayList()
    private lateinit var currentEntry: BmiHistoryEntry

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.bmi_data_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        currentEntry = entriesSet[position]
        holder.bmiVal.text = currentEntry.bmiVal
        holder.bmiVal.setTextColor(currentEntry.resultColorCode)

        holder.heightVal.text = currentEntry.height
        holder.weightVal.text = currentEntry.weight
        if(currentEntry.metricUnits) {
            holder.heightLabel.setText(R.string.bmi_main_height_metric_indication)
            holder.weightLabel.setText(R.string.bmi_main_weight_metric_indication)
        }
        else{
            holder.heightLabel.setText(R.string.bmi_main_height_imperial_indication)
            holder.weightLabel.setText(R.string.bmi_main_weight_imperial_indication)
        }

        val dateInstance =  DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
        holder.date.text = dateInstance.format(currentEntry.date)
    }

    override fun getItemCount() = entriesSet.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bmiVal: TextView = itemView.findViewById(R.id.bmiValTV)
        val heightVal: TextView = itemView.findViewById(R.id.heightValTV)
        val weightVal: TextView = itemView.findViewById(R.id.weightValTV)
        val date: TextView = itemView.findViewById(R.id.dateValTV)
        val heightLabel: TextView = itemView.findViewById(R.id.heightLabelTV)
        val weightLabel: TextView = itemView.findViewById(R.id.weightLabelTV)
    }
}