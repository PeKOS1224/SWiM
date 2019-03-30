package com.example.bmi


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bmi.Logic.BmiEntriesQueue
import com.example.bmi.Logic.BmiHistoryEntry
import kotlinx.android.synthetic.main.activity_history.*

import kotlinx.android.synthetic.main.toolbar.*
import com.example.bmi.MainActivity.Companion.HISTORY_SP
import com.example.bmi.MainActivity.Companion.HISTORY_SP_KEY
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HistoryActivity : AppCompatActivity() {

    private var bmiDataHistory:  BmiEntriesQueue = BmiEntriesQueue()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(bmi_menu)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val historySP = getSharedPreferences(HISTORY_SP, Context.MODE_PRIVATE)
        if(historySP.contains(HISTORY_SP_KEY)){
            val type = object : TypeToken<BmiEntriesQueue>() {}.type
            bmiDataHistory = Gson().fromJson<BmiEntriesQueue>(historySP.getString(HISTORY_SP_KEY, ""), type)
        }

        historyRV.layoutManager = LinearLayoutManager(this)
        historyRV.adapter = BmiHistoryAdapter(bmiDataHistory)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
