package com.wizeline.dependencyinjection.ui.checkout

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wizeline.dependencyinjection.data.TacoLocalDataSource
import com.wizeline.dependencyinjection.repository.TacoRepository
import com.wizeline.dependencyinjection.ui.checkout.compose.CheckoutScreen
import com.wizeline.dependencyinjection.util.DateFormatter
import com.wizeline.dependencyinjection.utils.Utils
import com.wizeline.dependencyinjection.utils.getOrAwaitValue
import com.wizeline.dependencyinjection.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
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
        coEvery { getAllTacos() } returns Utils.createTacoListWithId()
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

    @Test
    fun `display three items on lazy list and remove one item`() = runTest {
        composeTestRule.setContent {
            MaterialTheme {
                CheckoutScreen(dateFormatter = mockDateFormatter, viewModel = viewModel)
            }
        }
        val list = viewModel.tacoList.value!!
        composeTestRule.onNodeWithText(list[0].type).assertIsDisplayed()
        composeTestRule.onNodeWithText(list[1].type).assertIsDisplayed()
        composeTestRule.onNodeWithText(list[2].type).performScrollTo()
        composeTestRule.onNodeWithText(list[2].type).assertIsDisplayed()
        val itemOne = hasContentDescription("Close1")
        composeTestRule.onNode(itemOne).performClick()
        val newList = viewModel.tacoList.getOrAwaitValue()
        assertThat(newList.size).isEqualTo(2)
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
}
