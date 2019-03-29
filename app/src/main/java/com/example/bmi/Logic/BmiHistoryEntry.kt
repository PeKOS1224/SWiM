package com.example.bmi.Logic

import java.util.*

data class BmiHistoryEntry(val bmiVal: String, val resultColorCode: Int, val height: String,
                           val weight: String, val metricUnits:Boolean, val date: Date)