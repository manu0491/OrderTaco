package com.wizeline.dependencyinjection.ui.order

import android.os.Build
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.repository.TacoRepository
import com.wizeline.dependencyinjection.ui.OrderViewModel
import com.wizeline.dependencyinjection.ui.getOrderViewModelFactory
import com.wizeline.dependencyinjection.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
    application = HiltTestApplication::class
)
class OrderFragmentTest {

    //import com.wizeline.dependencyinjection.R agregada por mi
    private val testDispatcher = UnconfinedTestDispatcher()
    private val tacoRepository = mockk<TacoRepository>(relaxed = true)
    private val viewModel = spyk(OrderViewModel(tacoRepository,testDispatcher))
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)


    @Before
    fun `setup`(){
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @After
    fun `tearDown`(){
        clearAllMocks()
    }
    @Test
    fun `start fragment`() = runTest {
        val viewModelFactory = getOrderViewModelFactory(
            viewModel
        )
        launchFragmentInHiltContainer(
            fragmentClass = OrderFragment(viewModelFactory)
        ){
            assertThat(this).isInstanceOf(OrderFragment::class.java)
        }
    }

    @Test
    fun `check intial UI`() = runTest {
        val viewModelFactory = getOrderViewModelFactory(
            viewModel
        )
        launchFragmentInHiltContainer(
            fragmentClass = OrderFragment(viewModelFactory)
        ){
            onView(withId(R.id.title)).check(matches(withText(getString(R.string.tacos_order))))
            onView(withId(R.id.tortilla_selector_text)).check(matches(withText(getString(R.string.tortilla_selector))))
            onView(withId(R.id.button_add)).check(matches(withText(getString(R.string.add))))
            onView(withId(R.id.button_check)).check(matches(withText(getString(R.string.check))))
        }
    }

    @Test
    fun `add taco to order`() = runTest {
        val viewModelFactory = getOrderViewModelFactory(
            viewModel
        )
        launchFragmentInHiltContainer(
            fragmentClass = OrderFragment(viewModelFactory)
        ){
            val fragment = this as OrderFragment

            onView(withId(R.id.taco_spinner)).perform(click())
            val tacoType = "Pastor"
            onData(
                allOf(
                    `is`(instanceOf(String::class.java)),
                    `is`(tacoType)
                )
            ).perform(click())
            onView(withId(R.id.taco_spinner)).check(matches(withSpinnerText(containsString(tacoType))))
            onView(withId(R.id.radio_wheat)).perform(click())
            val note = "Mucha salsa"
            onView(withId(R.id.note)).perform(click()).perform(typeText(note))

            val taco = fragment.viewModel.taco.value

            assertThat(taco?.note.orEmpty()).isEqualTo(note)
        }
    }

}