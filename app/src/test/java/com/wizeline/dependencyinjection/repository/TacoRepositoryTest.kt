package com.wizeline.dependencyinjection.repository

import com.google.common.truth.Truth.assertThat
import com.wizeline.dependencyinjection.data.Taco
import com.wizeline.dependencyinjection.data.TacoDataSource
import com.wizeline.dependencyinjection.utils.Utils
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TacoRepositoryTest {

    private val mockTacoDataSource = mockk<TacoDataSource>(relaxed = true)
    private val sut = TacoRepository(mockTacoDataSource)

    @Before
    fun `setUp`(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { mockTacoDataSource.addTaco(any()) } just runs
    }


    @Test
    fun `add taco to local data source`() = runTest {
        //given
        val caputuredTaco = slot<Taco>()
        coEvery { mockTacoDataSource.addTaco(capture(caputuredTaco)) } just runs
        //when
        val taco = Utils.createTaco()
        sut.addLocalTaco(taco)

        //then
        assertThat(taco).isEqualTo(caputuredTaco.captured)
        coVerify(exactly = 1) { mockTacoDataSource.addTaco(taco) }
    }

    @Test
    fun `get all tacos from data source`() = runTest {
        //given
        val tacoList = Utils.createTacoList()
        coEvery { mockTacoDataSource.getAllTacos()} returns tacoList
        //when
        val localList = sut.getLocalAllTacos()

        //then
        assertThat(tacoList).isEqualTo(localList)
        coVerify(exactly = 1) { mockTacoDataSource.getAllTacos() }
    }

    @Test
    fun `remove all tacos `() = runTest{
        //given
        coEvery { mockTacoDataSource.removeTacos() } just runs
        //when
        sut.removeLocalTacos()

        //then
        coVerify(exactly = 1) { mockTacoDataSource.removeTacos() }
    }

    @Test
    fun `remove taco `() = runTest{
        //given
        val capturedTaco = slot<Taco>()
        coEvery { mockTacoDataSource.removeTaco(capture(capturedTaco)) } just runs
        //when
        val taco = Utils.createTaco()
        sut.removeTaco(taco)

        //then
        assertThat(capturedTaco.captured).isEqualTo(taco)
        coVerify(exactly = 1) { mockTacoDataSource.removeTaco(taco) }
    }
}