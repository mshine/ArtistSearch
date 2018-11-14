package com.lloyds.artistsearch

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

@LargeTest
class MainActivityEspressoTests {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun onAppLaunch_editTextAndButtonPresent() {
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
    }

    @Test
    fun onSearch_resultsAreShown() {
        onView(withId(R.id.search_edit_text)).perform(typeText("Jackson"))
        onView(withId(R.id.search_button)).perform(click())
        onView(withText("Michael Jackson")).check(matches(isDisplayed()))
    }

    @Test
    fun onSearch_emptyString_errorMessageShown() {
        onView(withId(R.id.search_edit_text)).perform(typeText(""))
        onView(withId(R.id.search_button)).perform(click())
        onView(withText(R.string.api_error)).inRoot(withDecorView(not(activityRule.activity.window.decorView))).check(
            matches(isDisplayed())
        )
    }

    @Test
    fun onResultClicked_showDetails() {
        onView(withId(R.id.search_edit_text)).perform(typeText("The"))
        onView(withId(R.id.search_button)).perform(click())
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition<ListAdapter.Item>(12))
        onView(withText("The White Stripes")).perform(click())
        onView(withId(R.id.artist_image)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_name)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_listeners)).check(matches(isDisplayed()))
        onView(withId(R.id.artist_url)).check(matches(isDisplayed()))
    }

    @Test
    fun onBackPressedFromArtistDetails_showMainActivity() {
        onView(withId(R.id.search_edit_text)).perform(typeText("Jackson"))
        onView(withId(R.id.search_button)).perform(click())
        onView(withText("Michael Jackson")).perform(click())
        pressBack()
        onView(withId(R.id.search_edit_text)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
    }
}
