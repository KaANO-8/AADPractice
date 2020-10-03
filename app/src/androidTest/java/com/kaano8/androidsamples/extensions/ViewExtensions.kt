package com.kaano8.androidsamples.extensions

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.CoreMatchers

fun ViewInteraction.assertIsDisplayed(): ViewInteraction {
    return this.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun ViewInteraction.assertNotDisplayed(): ViewInteraction {
    return this.check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))
}

fun ViewInteraction.assertEnabled(): ViewInteraction {
    return this.check(ViewAssertions.matches(ViewMatchers.isEnabled()))
}

fun ViewInteraction.assertClickable(): ViewInteraction {
    return this.check(ViewAssertions.matches(ViewMatchers.isClickable()))
}

fun ViewInteraction.assertNotClickable(): ViewInteraction {
    return this.check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isClickable())))
}