package com.wizeline.dependencyinjection.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest{

    @Rule
    @JvmField
    var activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun `start main option 1`(){
        ActivityScenario.launch(MainActivity::class.java).use { scenario ->
            scenario.onActivity {activity ->
                assertThat(activity.appNavigator).isNotNull()
                assertThat(activity).isInstanceOf(MainActivity::class.java)
            }
        }
    }

    @Test
    fun `start main option 2`(){
        activityRule.scenario.onActivity {activity ->
            assertThat(activity.appNavigator).isNotNull()
            assertThat(activity).isInstanceOf(MainActivity::class.java)
        }
    }

}