package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.content.Intent
import com.example.bmi.Logic.*
import com.example.bmi.MainActivity.Companion.INPUT_CORRECT_CODE
import com.example.bmi.MainActivity.Companion.INPUT_EMPTY_ERROR_CODE
import com.example.bmi.MainActivity.Companion.INPUT_NOT_INTEGER_ERROR_CODE
import com.example.bmi.MainActivity.Companion.INPUT_NOT_POSITIVE_ERROR_CODE
import com.example.bmi.R.layout.activity_main
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private var metricUnits = true

    companion object {
        const val INPUT_CORRECT_CODE = 1
        const val INPUT_NOT_POSITIVE_ERROR_CODE = -1
        const val INPUT_NOT_INTEGER_ERROR_CODE = -2
        const val INPUT_EMPTY_ERROR_CODE = -3

        const val KEY_RESULT_TEXT = "savedResultText"
        const val KEY_CATEGORY_TEXT = "savedCategoryText"
        const val KEY_COLOR_CODE = "savedColorCode"
        const val KEY_WEIGHT_INDICATION = "weightIndicationText"
        const val KEY_HEIGHT_INDICATION = "heightIndicationText"

        const val KEY_CATEGORY_NAME = "categoryName"
        const val KEY_HEIGHT_VAL = "heightValue"
        const val KEY_METRIC_UNITS = "metricUnits"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        countBtn.setOnClickListener {
            calculateBMIAndShowResults()
        }

        goToInfoButton.setOnClickListener {
            val myIntent = Intent(this, InfoActivity::class.java)
            myIntent.putExtra(KEY_CATEGORY_NAME, bmiGroupText.text.toString())
            myIntent.putExtra(KEY_HEIGHT_VAL, extractIntFromText(heightText))
            myIntent.putExtra(KEY_METRIC_UNITS, metricUnits)
            startActivity(myIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bmimenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
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
                val myIntent = Intent(this, HistoryActivity:: class.java)
                startActivity(myIntent)
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

        outState.putString(KEY_RESULT_TEXT, resText)
        outState.putString(KEY_CATEGORY_TEXT, categoryText)
        outState.putInt(KEY_COLOR_CODE, categoryColorCode)
        outState.putString(KEY_WEIGHT_INDICATION, weightIndicationText)
        outState.putString(KEY_HEIGHT_INDICATION, heightIndicationText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        resultText.text = savedInstanceState?.getString(KEY_RESULT_TEXT)
        bmiGroupText.text = savedInstanceState?.getString(KEY_CATEGORY_TEXT)
        resultText.setTextColor(savedInstanceState?.getInt(KEY_COLOR_CODE)!!)
        bmiGroupText.setTextColor(savedInstanceState.getInt(KEY_COLOR_CODE))
        weightIndication.text = savedInstanceState.getString(KEY_WEIGHT_INDICATION)
        heightIndication.text = savedInstanceState.getString(KEY_HEIGHT_INDICATION)

    }

    private fun extractIntFromText(textWithNumber: EditText): Int {
        if(validateInput(textWithNumber) == INPUT_CORRECT_CODE)
            return textWithNumber.text.toString().toInt()
        return 0
    }

    private fun calculateBMIAndShowResults() {
        if (!setErrorsAndReturnErrorFlag(weightText, getString(R.string.weight_field_name)) &&
            !setErrorsAndReturnErrorFlag(heightText, getString(R.string.height_field_name))) {
            val weightValue = extractIntFromText(weightText)
            val heightValue = extractIntFromText(heightText)
            val bmiCalculator: BMI
            if (metricUnits)
                bmiCalculator = BmiForKgCm(weightValue, heightValue)
            else
                bmiCalculator = BmiForInchesPounds(weightValue, heightValue)
            val result = bmiCalculator.countBMI()
            setGroupAndColorDependingOnBmiValue(result)
            resultText.text = "%.2f".format(result)
        }
    }

    private fun switchUnits() {
        metricUnits = !metricUnits
        bmiGroupText.text = ""
        resultText.setTextColor(resources.getColor(R.color.colorSecondary, theme))
        resultText.setText(R.string.bmi_main_result_default_text)
        weightText.text.clear()
        heightText.text.clear()

        if (metricUnits) {
            weightIndication.setText(R.string.bmi_main_weight_metric_indication)
            heightIndication.setText(R.string.bmi_main_height_metric_indication)
        } else {
            weightIndication.setText(R.string.bmi_main_weight_imperial_indication)
            heightIndication.setText(R.string.bmi_main_height_imperial_indication)
        }
    }

    private fun setGroupAndColorDependingOnBmiValue(bmiVal: Double) {
        var categoryText = R.string.bmi_category_normal
        val categoryColorCode: Int
        when {
            bmiVal < BMI_LIMIT_BOTTOM -> {
                categoryText = R.string.bmi_category_sev_underweight
                categoryColorCode = resources.getColor(R.color.navyBlue, theme)
            }
            bmiVal < BMI_LIMIT_LOWER -> {
                categoryText = R.string.bmi_category_underweight
                categoryColorCode = resources.getColor(R.color.lapisLazuli, theme)
            }
            bmiVal < BMI_LIMIT_UPPER ->
                categoryColorCode = resources.getColor(R.color.verdigris, theme)
            bmiVal < BMI_LIMIT_TOP -> {
                categoryText = R.string.bmi_category_overweight
                categoryColorCode = resources.getColor(R.color.rose, theme);
            }
            else -> {
                categoryText = R.string.bmi_category_sev_overweight
                categoryColorCode = resources.getColor(R.color.pompeianRose, theme)
            }
        }
        bmiGroupText.text = getString(categoryText)
        resultText.setTextColor(categoryColorCode)
        bmiGroupText.setTextColor(categoryColorCode)

    }

    private fun setErrorsAndReturnErrorFlag(bmiInput: EditText, fieldName: String): Boolean {
        val proprietyCode = validateInput(bmiInput)
        var errorFlag = true
        when (proprietyCode) {
            INPUT_CORRECT_CODE ->
                errorFlag = false
            INPUT_EMPTY_ERROR_CODE ->
                bmiInput.error = "enter $fieldName"
            INPUT_NOT_INTEGER_ERROR_CODE ->
                bmiInput.error = "enter $fieldName as integer"
            INPUT_NOT_POSITIVE_ERROR_CODE ->
                bmiInput.error = "enter positive $fieldName value"
        }

        return errorFlag
    }

    private fun validateInput(bmiInput: EditText): Int {
        return if (bmiInput.text.isNotEmpty()) {
            val extractedValue = bmiInput.text.toString().toIntOrNull()
            if (extractedValue != null) {
                if (extractedValue != 0)
                    INPUT_CORRECT_CODE
                else
                    INPUT_NOT_POSITIVE_ERROR_CODE
            } else
                INPUT_NOT_INTEGER_ERROR_CODE
        } else
            INPUT_EMPTY_ERROR_CODE
    }
}

