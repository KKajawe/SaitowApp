package com.example.saitowapp.ui_classes


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.saitowapp.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainActivityTest() {

       onView(withId(R.id.edt_username)).perform(replaceText("106901"), closeSoftKeyboard())

        onView(withId(R.id.edt_password)).perform(replaceText("Mobile20Dev!"), closeSoftKeyboard())


     onView(withId(R.id.btn_login)).perform(click())
    }
}
