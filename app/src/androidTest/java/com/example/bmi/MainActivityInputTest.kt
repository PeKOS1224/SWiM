package com.example.bmi

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityInputTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {
        val weightEditText = onView(withId(R.id.weightText))
        weightEditText.perform(replaceText("70"), closeSoftKeyboard())

        val heightEditText = onView(withId(R.id.heightText))
        heightEditText.perform(replaceText("180"), closeSoftKeyboard())

        val countBmiButton = onView(withId(R.id.countBtn))
        countBmiButton.perform(click())

        val resultTextView = onView(withId(R.id.resultText))
        resultTextView.check(matches(withText("21,60")))
    }
}
