package com.jjh.android.game

import android.view.Gravity
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testActivityState() {
        val state = activityRule.scenario.state
        Assert.assertEquals("activity state should be", Lifecycle.State.RESUMED, state)
    }

    @Test
    fun testNavigationDrawIsClosed() {
        currentActivity.run {
            // Check drawer is closed
            Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.START)))
        }
    }

    @Test
    fun testNavigationDrawOpens() {
        currentActivity.run {
            // Open the navigation drawer
            Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                    .perform(DrawerActions.open())
                    .check(ViewAssertions.matches(DrawerMatchers.isOpen())); // check it is open
        }
    }

    @Test
    fun clickOnHomeNavigationItem_ShowsHomeScreen() {
        // Open Drawer to click on navigation.
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))
                .check(ViewAssertions.matches(DrawerMatchers.isClosed(Gravity.START))) // Left Drawer should be closed.
                .perform(DrawerActions.open()) // Open Drawer

        // Start the screen of your activity.
        Espresso.onView(ViewMatchers.withId(R.id.nav_view))
                .perform(NavigationViewActions.navigateTo(R.id.nav_home))

        // Check that the Activity was opened.
        Espresso.onView(ViewMatchers.withId(R.id.text_home))
                .check(ViewAssertions.matches(ViewMatchers.withText("This is home Fragment")))
    }

    private val currentActivity: MainActivity
        get() {
            var activity: MainActivity? = null
            activityRule.scenario.onActivity {
                activity = it
            }
            return activity!!
        }

}