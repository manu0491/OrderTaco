package com.wizeline.dependencyinjection.data

import com.google.common.truth.Truth.assertThat
import com.wizeline.dependencyinjection.utils.Utils.createTaco
import com.wizeline.dependencyinjection.utils.Utils.createTacoList
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TacoLocalDataSourceTest{


    private lateinit var sut: TacoLocalDataSource
    private val mockTacoDao = mockk<TacoDao>(relaxed = true)

    @Before
    fun `setUp`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        sut = TacoLocalDataSource(mockTacoDao)
        coEvery { mockTacoDao.deleteTaco() } just Runs
        coEvery { mockTacoDao.deleteTable() } just Runs
    }

    @Test
    fun `create taco in taco dao`() = runTest {
        //given
        val taco = createTaco()
        val captutedTaco = slot<Taco>()
        coEvery { mockTacoDao.inserAll(capture(captutedTaco)) } just runs

        //when
        sut.addTaco(taco)

        //then
        assertThat(taco).isEqualTo(captutedTaco.captured)
        coVerify(exactly = 1) { mockTacoDao.inserAll(taco) }
    }

    @Test
    fun `get all tacos from taco dao`() = runTest {
        //given
        coEvery { mockTacoDao.getAll() } returns createTacoList()

        //when
        val tacoList = mutableListOf<Taco>().apply {
            addAll(sut.getAllTacos())
        }

        //then
        assertThat(tacoList).isEqualTo(createTacoList())
        coVerify(exactly = 1) { mockTacoDao.getAll() }

    }

    @Test
    fun `delete taco table in taco dao`() = runTest {
        //given
        //when
        sut.removeTacos()

        //then
        coVerify(exactly = 1) { mockTacoDao.deleteTable() }

    }

    @Test
    fun `delete taco in taco dao`() = runTest {
        //given
        val taco = createTaco()
        val captutedTaco = slot<Taco>()
        coEvery { mockTacoDao.deleteTaco(capture(captutedTaco)) } just runs
        //when
        sut.removeTaco(taco)

        //then
        assertThat(taco).isEqualTo(captutedTaco.captured)
        coVerify(exactly = 1) { mockTacoDao.deleteTaco(taco) }

    }

}