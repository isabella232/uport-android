/*
 * Copyright (c) 2018. uPort
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.uport.android

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import me.uport.android.onboarding.Onboarding
import me.uport.sdk.Uport
import me.uport.sdk.identity.Account
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.inject
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class OnboardingFlowTest : KoinTest {

    @JvmField
    @Rule
    val activityRule = ActivityTestRule(NavHostActivity::class.java, true, false)

    @Before
    fun run_before_every_test() {

    }


    @Test
    fun application_startup_with_blank_state_leads_to_onboarding_screen() {

        val mockUport = Uport
        //this should not be so easily done, but we'll have to work with it until deleteAccount is implemented in SDK ( https://github.com/uport-project/uport-android-sdk/issues/10 )
        mockUport.defaultAccount = null
        loadKoinModules(listOf(applicationContext {
            bean { mockUport }
        }))

        val onboarding: Onboarding by inject()

        onboarding.clearUser()

        activityRule.launchActivity(null)

        onView(withId(R.id.onboarding_nav_host_frag)).check(matches(isDisplayed()))
    }
}