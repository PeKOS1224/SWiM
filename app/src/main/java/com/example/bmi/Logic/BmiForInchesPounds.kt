package com.example.bmi.Logic


class BmiForInchesPounds(var mass: Int, var height: Int) : BMI {
    override fun countBMI(): Double {
        return (mass * 703.0 / (height * height))
    }

    override fun calculateIdealWeight(): Double {
        return IDEAL_BMI_VALUE * (height* height) * 1.0 / 703.0
    }

}