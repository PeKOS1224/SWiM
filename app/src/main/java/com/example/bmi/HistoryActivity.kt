package com.example.bmi


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

import kotlinx.android.synthetic.main.toolbar.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(bmi_menu)

        val bmiDataHistory:  ArrayList<String> = ArrayList(10)

        for(i in 1..10){
            bmiDataHistory.add("Data $i")
        }
        historyRV.layoutManager = LinearLayoutManager(this)
        historyRV.adapter = BmiHistoryAdapter(bmiDataHistory)
    }

}
