package com.example.rovermore.gitrepositoriesrm

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.example.rovermore.gitrepositoriesrm.detail.DetailView
import com.example.rovermore.gitrepositoriesrm.main.MainView
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainViewSearchTest : AndroidJUnitRunner() {


    @get:Rule
    private val activityRule = ActivityTestRule(
        MainView::class.java, false, false)


    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<MainView> = IntentsTestRule(
        MainView::class.java
    )

    @Before
    fun stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))
    }

    @Test
    @Throws(Exception::class)
    fun testSearch() {
        Espresso.onView(ViewMatchers.withId(R.id.et_search))
            .perform(ViewActions.replaceText("tetris"))

        Espresso.onView(ViewMatchers.withId(R.id.button_search))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<MainAdapter.MyViewHolder>(0, ViewActions.click())
        )

        intended(
            allOf(
                hasExtra("login", "chvin"),
                hasExtra("repository", "react-tetris"),
                hasComponent(DetailView::class.java.name)
            )
        )

    }

    @Test
    fun testRecyclerViewDisplays(){

        Espresso.onView(withId(R.id.recycler_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }
}