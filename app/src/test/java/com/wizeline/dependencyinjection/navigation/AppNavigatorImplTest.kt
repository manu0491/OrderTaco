package com.wizeline.dependencyinjection.navigation

import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.junit.Before
import org.junit.Test


class AppNavigatorImplTest{

     private val sut: AppNavigator = mockk(relaxed = true)

    @Before
    fun `setup`() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        every { sut.navigateTo(any()) } just runs
        every { sut.navigateTo(any()) } returns Unit
        every { sut.navigateTo(any()) } answers {
            runs
        }
    }

    @Test
    fun `go to order screen`(){
        //given
        val captureNavigate = slot<Screens>()
        every { sut.navigateTo(capture(captureNavigate)) } just runs
        //when
        sut.navigateTo(Screens.ORDER)

        //then
        assertThat(Screens.ORDER).isEqualTo(captureNavigate.captured)
        verify(exactly = 1) { sut.navigateTo(any()) }
    }

    @Test
    fun `go to checkout screen`(){
        //given
        val captureNavigate = slot<Screens>()
        every { sut.navigateTo(capture(captureNavigate)) } just runs
        //when
        sut.navigateTo(Screens.CHECKOUT)

        //then
        assertThat(Screens.CHECKOUT).isEqualTo(captureNavigate.captured)
        verify(exactly = 1) { sut.navigateTo(any()) }
    }
}