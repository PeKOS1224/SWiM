package com.example.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class InfoActivityLayoutTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun infoActivityLayoutTest() {
        val infoButton = onView(withId(R.id.goToInfoButton))
        infoButton.perform(click())

        val reactionImg = onView(withId(R.id.bmiReaction))
        reactionImg.check(matches(isDisplayed()))

        val leftTV = onView(withId(R.id.infoTextLeft))
        leftTV.check(matches(isDisplayed()))

        val rightTV = onView(withId(R.id.infoTextRight))
        rightTV.check(matches(isDisplayed()))
    }
}
