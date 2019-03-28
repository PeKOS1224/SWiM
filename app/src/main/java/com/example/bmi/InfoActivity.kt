package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi.Logic.BMI
import com.example.bmi.Logic.BmiForInchesPounds
import com.example.bmi.Logic.BmiForKgCm
import kotlinx.android.synthetic.main.activity_info_content.*
import kotlinx.android.synthetic.main.toolbar.*

class InfoActivity : AppCompatActivity() {
    private var bmiCategoryName:String = ""
    private var heightValue:Int = 0
    private var metricUnits:Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        setSupportActionBar(bmi_menu)
        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.bmi_info_label)

        actionBar.setDisplayHomeAsUpEnabled(true)

        bmiCategoryName = intent.getStringExtra(MainActivity.KEY_CATEGORY_NAME)
        heightValue = intent.getIntExtra(MainActivity.KEY_HEIGHT_INDICATION, 0)
        metricUnits = intent.getBooleanExtra(MainActivity.KEY_METRIC_UNITS, true)
        setScreenAccordinglyToCategory()
        setScreenAccordinglyToHeight()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setScreenAccordinglyToHeight() {
        val bmiCalculator: BMI
        val weightUnit:String
        if(metricUnits) {
            weightUnit = getString(R.string.metric_weight_unit)
            bmiCalculator = BmiForKgCm(0, heightValue)
        }
        else {
            weightUnit = getString(R.string.imperial_weight_unit)
            bmiCalculator = BmiForInchesPounds(0, heightValue)
        }
        val idealWeight = bmiCalculator.calculateIdealWeight()
        val strIdealWeight = "%.2f".format(idealWeight)
        if(idealWeight > 0.0) {
            val matchingRightText = "Ideal weight for your height would be $strIdealWeight $weightUnit"
            infoTextRight.text = matchingRightText
        }
    }
    
    private fun setScreenAccordinglyToCategory() {
        val matchingLeftText:String
        when (bmiCategoryName) {
            MainActivity.OK_CATEGORY_NAME -> {
                bmiReaction.setImageResource(R.drawable.happy)
                matchingLeftText = getString(R.string.info_healthy_weight_description)
            }
            MainActivity.OVERWEIGHT_CATEGORY_NAME, MainActivity.UNDERWEIGHT_CATEGORY_NAME -> {
                bmiReaction.setImageResource(R.drawable.worried)
                matchingLeftText = getString(R.string.info_over_under_weight_description)
            }
            MainActivity.SEV_OVERWEIGHT_CATEGORY_NAME, MainActivity.SEV_UNDERWEIGHT_CATEGORY_NAME -> {
                bmiReaction.setImageResource(R.drawable.sad)
                matchingLeftText = getString(R.string.info_sev_over_under_weight_description)
            }
            else ->
                return
        }
        infoTextLeft.text = matchingLeftText
    }
}
