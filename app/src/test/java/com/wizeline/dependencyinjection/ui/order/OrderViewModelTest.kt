package com.wizeline.dependencyinjection.ui.order

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoDataSource
import com.wizeline.dependencyinjection.repository.TacoRepository
import com.wizeline.dependencyinjection.ui.OrderViewModel
import com.wizeline.dependencyinjection.ui.TACO_STATE
import com.wizeline.dependencyinjection.ui.TacoUiState
import com.wizeline.dependencyinjection.utils.Utils
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.LooperMode

@OptIn(ExperimentalCoroutinesApi::class)
@LooperMode(LooperMode.Mode.PAUSED)
@RunWith(AndroidJUnit4::class)
class OrderViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    //val testScheduler = TestCoroutineScheduler()
    //val testDispatcher = StandardTestDispatcher(testScheduler)
    //val testScope = TestScope(testDispatcher)

    private val tacoDataSource = mockk<TacoDataSource>(relaxed = true)
    private val tacoRepository = mockk<TacoRepository>(relaxed = true)
    private val sut = OrderViewModel(tacoRepository, testDispatcher)

    @Before
    fun `setup`(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { tacoRepository.addLocalTaco(any()) } returns Unit
        coEvery { tacoDataSource.addTaco(any()) } just runs
        coEvery { tacoRepository.removeTaco(any()) } just runs
        coEvery { tacoRepository.removeLocalTacos() } just runs
        coEvery { tacoRepository.getLocalAllTacos() } returns Utils.createTacoList()
    }

    //https://developer.android.com/kotlin/coroutines/test?hl=es-419
    @Test
    fun`add taco to order`()= runTest {
        //given
        val taco = Utils.createTaco()
        sut.setType(taco.type)
        sut.setTortilla(taco.tortilla)
        sut.setNote(taco.note.orEmpty())

        //when
        val capturedTaco = slot<Taco>()
        coEvery { tacoRepository.addLocalTaco(capture(capturedTaco)) } just runs
        sut.addTacoToOrder()
        advanceUntilIdle()

        //then
        val tacoLiveData = sut.taco.value
        tacoLiveData?.let {
            assertThat(it).isInstanceOf(Taco::class.java)
            assertThat(it).isEqualTo(sut.emptyTaco)
            val tacoTimeMillis = taco.copy(timestamp = capturedTaco.captured.timestamp)
            assertThat(capturedTaco.captured).isInstanceOf(Taco::class.java)
            assertThat(capturedTaco.captured).isEqualTo(tacoTimeMillis)
        }
    }

    @Test
    fun `checking state loading`() = runTest{
        val ordering = sut.tacoState.value as TacoUiState.OrderingState
        assertThat(ordering.ordering).isEqualTo(TACO_STATE.LOADING)
    }

    @Test
    fun `checking state ordering`() = runTest{
        //given
        val taco = Utils.createTaco()

        //when
        sut.setType(taco.type)
        sut.setTortilla(taco.tortilla)
        sut.setNote(taco.note.orEmpty())

        //then
        val ordering = sut.tacoState.value as TacoUiState.OrderingState
        assertThat(ordering.ordering).isEqualTo(TACO_STATE.ORDERING)
    }

    @Test
    fun `checking state done`() = runTest{
        //given
        val taco = Utils.createTaco()
        sut.setType(taco.type)
        sut.setTortilla(taco.tortilla)
        sut.setNote(taco.note.orEmpty())

        //when
        val capturedTaco = slot<Taco>()
        coEvery { tacoRepository.addLocalTaco(capture(capturedTaco)) } just runs
        sut.addTacoToOrder()
        advanceUntilIdle()

        //then
        val ordering = sut.tacoState.value as TacoUiState.OrderingState
        assertThat(ordering.ordering).isEqualTo(TACO_STATE.DONE)
    }


}