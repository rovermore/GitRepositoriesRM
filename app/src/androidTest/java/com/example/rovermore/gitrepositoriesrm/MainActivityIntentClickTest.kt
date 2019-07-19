package com.example.rovermore.gitrepositoriesrm

import android.app.Activity
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.runner.AndroidJUnit4
import androidx.test.runner.AndroidJUnitRunner
import com.example.rovermore.gitrepositoriesrm.adapters.MainAdapter
import com.example.rovermore.gitrepositoriesrm.detail.DetailActivity
import com.example.rovermore.gitrepositoriesrm.main.MainActivity
import org.hamcrest.CoreMatchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityIntentClickTest : AndroidJUnitRunner() {


    @Rule
    @JvmField
    var mActivityRule: IntentsTestRule<MainActivity> = IntentsTestRule(
        MainActivity::class.java
    )

    @Before
    fun stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(ActivityResult(Activity.RESULT_OK, null))
    }


    @Test
    fun testClickRecyclerViewItem() {
        Espresso.onView(withId(R.id.recycler_view)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<MainAdapter.MyViewHolder>(0, ViewActions.click())
        )


        intended(
            allOf(
                hasExtra("login", "mojombo"),
                hasExtra("repository", "grit"),
                hasComponent(DetailActivity::class.java.name)
            )
        )
    }

    @Test
    fun testRecyclerViewDisplays(){
        Espresso.onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }
}