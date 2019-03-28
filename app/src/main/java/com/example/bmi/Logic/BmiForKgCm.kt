package com.example.bmi.Logic

class BmiForKgCm (var mass: Int, var height: Int) : BMI {
    override fun countBMI(): Double {
        return mass * 10000.0 / (height * height)
    }

    override fun calculateIdealWeight(): Double {
        return IDEAL_BMI_VALUE * (height* height) / 10000.0
    }
}