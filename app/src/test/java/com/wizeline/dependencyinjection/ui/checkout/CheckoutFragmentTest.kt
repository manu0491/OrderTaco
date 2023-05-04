package com.wizeline.dependencyinjection.ui.checkout

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoLocalDataSource
import com.wizeline.dependencyinjection.repository.TacoRepository
import com.wizeline.dependencyinjection.ui.checkout.compose.CheckoutScreen
import com.wizeline.dependencyinjection.util.DateFormatter
import com.wizeline.dependencyinjection.utils.Utils
import com.wizeline.dependencyinjection.utils.Utils.createId
import com.wizeline.dependencyinjection.utils.getOrAwaitValue
import com.wizeline.dependencyinjection.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
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
class CheckoutFragmentTest{
    @get:Rule
    val composeTestRule = createComposeRule()

    val mockDateFormatter = mockk<DateFormatter>(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()
    private val tacoLocalDataSource = mockk<TacoLocalDataSource>(relaxed = true){
        coEvery { getAllTacos() } returns Utils.createTacoList().createId()
    }
    private val mockTacoRepository = spyk(TacoRepository(tacoLocalDataSource))
    private val viewModel = spyk(CheckoutViewModel(mockTacoRepository,testDispatcher))
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @Before
    fun `setup`(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { mockDateFormatter.formatDate(any()) } returns "Today"
    }

    @After
    fun `tearDown`(){
        clearAllMocks()
    }
    @Test
    fun `display three items on lazy list and remove one item`() = runTest {
        //Create the UI
        composeTestRule.setContent {
            MaterialTheme {
                CheckoutScreen(dateFormatter = mockDateFormatter, viewModel = viewModel)
            }
        }
        //Check if the taco list exist
        val list = viewModel.tacoList.value!!
        composeTestRule.onNodeWithText(list[0].type).assertIsDisplayed()
        composeTestRule.onNodeWithText(list[1].type).assertIsDisplayed()
        composeTestRule.onNodeWithText(list[2].type).performScrollTo()
        composeTestRule.onNodeWithText(list[2].type).assertIsDisplayed()
        //Removed the taco
        val itemOne = hasContentDescription("Close1")
        composeTestRule.onNode(itemOne).performClick()
        val newList = viewModel.tacoList.getOrAwaitValue()
        //Assert and verify results
        assertThat(newList.size).isEqualTo(2)
        coVerify(exactly = 1) { mockTacoRepository.removeTaco(any()) }
        coVerify(exactly = 1) { tacoLocalDataSource.removeTaco(any()) }
    }

    @Test
    fun `test using Container`(){

        launchFragmentInHiltContainer<CheckoutFragment>{
            val fragment = this as CheckoutFragment
            fragment.binding.composeView.setContent {
                MaterialTheme {
                    CheckoutScreen(dateFormatter = mockDateFormatter, viewModel = viewModel)
                }
            }
            composeTestRule.onNodeWithText("Suadero").assertIsDisplayed()

        }
    }

    @Test
    fun `review if the removed taco is the first element in the list`(){
        //Create the UI
        composeTestRule.setContent {
            MaterialTheme {
                CheckoutScreen(dateFormatter = mockDateFormatter, viewModel = viewModel)
            }
        }
        //Check if the taco list exist
        val list = viewModel.tacoList.value!!
        composeTestRule.onNodeWithText(list[0].type).assertIsDisplayed()
        composeTestRule.onNodeWithText(list[1].type).assertIsDisplayed()
        composeTestRule.onNodeWithText(list[2].type).performScrollTo()
        composeTestRule.onNodeWithText(list[2].type).assertIsDisplayed()
        //Capture the removed taco
        val captureTaco = slot<Taco>()
        coEvery { tacoLocalDataSource.removeTaco(capture(captureTaco)) } just runs

        //Removed the taco
        val itemOne = hasContentDescription("Close1")
        composeTestRule.onNode(itemOne).performClick()
        //Wait until tacoList change
        val newList = viewModel.tacoList.getOrAwaitValue()
        //Assert and verify results
        assertThat(newList.size).isEqualTo(2)
        coVerify(exactly = 1) { mockTacoRepository.removeTaco(any()) }
        coVerify(atLeast = 1) { tacoLocalDataSource.removeTaco(any()) }
        assertThat(captureTaco.captured).isEqualTo(list[0])
    }
}
