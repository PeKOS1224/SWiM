package com.example.bmi


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
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
class MainActivityUnitsChangeTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityUnitsChangeTest() {
        val weightETBefore = onView(withId(R.id.weightText))
        weightETBefore.check(matches(withHint(R.string.bmi_main_weight_hint)))
        weightETBefore.perform(replaceText("70"), closeSoftKeyboard())

        val heightETBefore = onView(withId(R.id.heightText))
        heightETBefore.check(matches(withHint(R.string.bmi_main_height_hint)))
        heightETBefore.perform(replaceText("180"), closeSoftKeyboard())

        val resultTVBefore = onView(withId(R.id.resultText))
        resultTVBefore.check(matches(withText(R.string.bmi_main_result_default_text)))

        val countBmiButton = onView(withId(R.id.countBtn))
        countBmiButton.perform(click())

        Thread.sleep(300)

       // val overflowMenuButton = onView(withId(R.id.toolbar))
        val overflowMenuButton = onView(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.toolbar),
                        1
                    ),
                    0
                )
            )
        overflowMenuButton.perform(click())

        Thread.sleep(250)

        val changeUnitsButton = onView(withText(R.string.bmi_menu_change_units))
        changeUnitsButton.perform(click())

        val weightETAfter = onView(withId(R.id.weightText))
        weightETAfter.check(matches(withHint(R.string.bmi_main_weight_hint)))

        val heightETAfter = onView(withId(R.id.heightText))
        heightETAfter.check(matches(withHint(R.string.bmi_main_height_hint)))

        val resultTVAfter = onView(withId(R.id.resultText))
        resultTVAfter.check(matches(withText(R.string.bmi_main_result_default_text)))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
