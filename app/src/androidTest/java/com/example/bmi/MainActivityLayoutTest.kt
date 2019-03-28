package com.example.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityLayoutTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityUINecessaryElementsTest() {
        val weightIndTV = onView(withId(R.id.weightIndication))
        weightIndTV.check(matches(isDisplayed()))

        val weightET = onView(withId(R.id.weightText))
        weightET.check(matches(isDisplayed()))

        val heightIndTV = onView(withId(R.id.heightIndication))
        heightIndTV.check(matches(isDisplayed()))

        val heightET = onView(withId(R.id.heightText))
        heightET.check(matches(isDisplayed()))

        val countBmiButton = onView(withId(R.id.countBtn))
        countBmiButton.check(matches(isDisplayed()))

        val infoButton = onView(withId(R.id.goToInfoButton))
        infoButton.check(matches(isDisplayed()))
    }
}
