package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.content.Intent
import com.example.bmi.Logic.*
import com.example.bmi.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private var metricUnits = true
    private var lastHeightValue = 0
    private var lastWeightValue = 0

    companion object{
        const val SEV_UNDERWEIGHT_CATEGORY_NAME = "severely underweight"
        const val UNDERWEIGHT_CATEGORY_NAME = "underweight"
        const val OK_CATEGORY_NAME = "normal weight"
        const val OVERWEIGHT_CATEGORY_NAME = "overweight"
        const val SEV_OVERWEIGHT_CATEGORY_NAME = "severely overweight"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        countBtn.setOnClickListener {
            calculateBMIAndShowResults()
        }

        goToInfoButton.setOnClickListener{
            val myIntent = Intent(this, InfoActivity::class.java)
            myIntent.putExtra("categoryName", bmiGroupText.text.toString())
            myIntent.putExtra("heightValue", lastHeightValue)
            myIntent.putExtra("metricUnits", metricUnits)
            startActivity(myIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bmimenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.changeUnits -> {
                switchUnits()
                true
            }
            R.id.aboutMe -> {
                val myIntent = Intent(this, ActivityAboutMe::class.java)
                startActivity(myIntent)
                true
            }
            R.id.history -> {
                //TODO implement this till Sunday
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val resText = resultText.text.toString()
        val categoryText = bmiGroupText.text.toString()
        val categoryColorCode = resultText.currentTextColor
        val weightIndicationText = weightIndication.text.toString()
        val heightIndicationText = heightIndication.text.toString()

        outState.putString("savedResultText", resText)
        outState.putString("savedCategoryText", categoryText)
        outState.putInt("savedColorCode", categoryColorCode)
        outState.putString("weightIndicationText", weightIndicationText)
        outState.putString("heightIndicationText", heightIndicationText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        resultText.text = savedInstanceState?.getString("savedResultText")
        bmiGroupText.text = savedInstanceState?.getString("savedCategoryText")
        resultText.setTextColor(savedInstanceState?.getInt("savedColorCode")!!)
        bmiGroupText.setTextColor(savedInstanceState.getInt("savedColorCode"))
        weightIndication.text = savedInstanceState.getString("weightIndicationText")
        heightIndication.text = savedInstanceState.getString("heightIndicationText")

    }

    private fun extractIntFromText(textWithNumber: EditText) : Int{
        val strText = textWithNumber.text.toString()
        return if(strText != "")
            strText.toInt()
        else
            0
    }

    private fun calculateBMIAndShowResults(){
        if(isInputValid(weightText, "weight") && isInputValid(heightText, "height")) {
            lastWeightValue = extractIntFromText(weightText)
            lastHeightValue = extractIntFromText(heightText)
            val bmiCalculator: BMI
            if(metricUnits)
                bmiCalculator = BmiForKgCm(lastWeightValue, lastHeightValue)
            else
                bmiCalculator = BmiForInchesPounds(lastWeightValue, lastHeightValue)
            val result = bmiCalculator.countBMI()
            setGroupAndColorDependingOnBmiValue(result)
            resultText.text = "%.2f".format(result)
        }
    }

    private fun switchUnits(){
        metricUnits = !metricUnits
        bmiGroupText.text = ""
        resultText.setTextColor(resources.getColor(R.color.colorSecondary, theme))
        resultText.setText(R.string.bmi_main_result_default_text)
        weightText.text.clear()
        heightText.text.clear()

        if(metricUnits){
            weightIndication.setText(R.string.bmi_main_weight_metric_indication)
            heightIndication.setText(R.string.bmi_main_height_metric_indication)
        }
        else{
            weightIndication.setText(R.string.bmi_main_weight_imperial_indication)
            heightIndication.setText(R.string.bmi_main_height_imperial_indication)
        }
    }

    private fun setGroupAndColorDependingOnBmiValue(bmiVal: Double){
        var categoryText = OK_CATEGORY_NAME
        val categoryColorCode:Int
        when {
            bmiVal < BMI_LIMIT_BOTTOM -> {
                categoryText = SEV_UNDERWEIGHT_CATEGORY_NAME
                categoryColorCode = resources.getColor(R.color.navyBlue, theme)
            }
            bmiVal < BMI_LIMIT_LOWER -> {
                categoryText = UNDERWEIGHT_CATEGORY_NAME
                categoryColorCode = resources.getColor(R.color.lapisLazuli, theme)
            }
            bmiVal < BMI_LIMIT_UPPER ->
                categoryColorCode = resources.getColor(R.color.verdigris, theme)
            bmiVal < BMI_LIMIT_TOP -> {
                categoryText = OVERWEIGHT_CATEGORY_NAME
                categoryColorCode = resources.getColor(R.color.rose, theme);
            }
            else -> {
                categoryText = SEV_OVERWEIGHT_CATEGORY_NAME
                categoryColorCode = resources.getColor(R.color.pompeianRose, theme)
            }
        }
        bmiGroupText.text = categoryText
        resultText.setTextColor(categoryColorCode)
        bmiGroupText.setTextColor(categoryColorCode)

    }

    private fun isInputValid(bmiInput: EditText, fieldName: String) : Boolean{

        if(bmiInput.text.isNotEmpty()) {
            val extractedValue = bmiInput.text.toString().toIntOrNull()
            return if (extractedValue != null) {
                if (extractedValue != 0)
                    true
                else {
                    bmiInput.error = "enter positive $fieldName value"
                    false
                }
            }
            else {
                bmiInput.error = "enter $fieldName as integer"
                false
            }
        }
        else{
            bmiInput.error = "enter $fieldName"
            return false
        }
    }
}
