package com.example.unittestingsamplekotlin

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import kotlinx.coroutines.test.runTest

import org.junit.Assert.*
import org.junit.Before
import org.mockito.kotlin.any
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    // mock data source
    private val repo = mockk<MainRepo>(relaxed = true)
    // or, you can use it this way:
    /*
    @MockK
    lateinit var repo: MainRepo
    @Before
    fun setUp() = MockKAnnotations.init(this)
     */

    // create an instance of the system under testing
    private val viewModel = MainViewModel(repo)

    @Test
    fun `no internet connection`() = runTest {
        coEvery { repo.getClass(any()) } throws IOException()

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, MainViewModelMockData.noInternetDeserialized)
    }

    @Test
    fun `ok parcelable`() = runTest {
        coEvery { repo.getClass(any()) } returns MainViewModelMockData.ok

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, MainViewModelMockData.okDeserialized)
    }

    @Test
    fun `ok null`() = runTest {
        coEvery { repo.getClass(any()) } returns MainViewModelMockData.okNull

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, null)
    }

    @Test
    fun `ok garbage`() = runTest {
        coEvery { repo.getClass(any()) } returns MainViewModelMockData.okGarbage

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, MainViewModelMockData.unexpectedErrorDeserialized)
    }


    @Test
    fun `bad request parcelable`() = runTest {
        coEvery { repo.getClass(any()) } returns MainViewModelMockData.badRequest

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, MainViewModelMockData.badRequestDeserialized)
    }

    @Test
    fun `bad request garbage`() = runTest {
        coEvery { repo.getClass(any()) } returns MainViewModelMockData.badRequestGarbage

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, MainViewModelMockData.unexpectedErrorDeserialized)
    }

    @Test
    fun `bad request null`() = runTest {
        coEvery { repo.getClass(any()) } returns MainViewModelMockData.badRequestNull

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, null)
    }

    @Test
    fun `server error`() = runTest {
        coEvery { repo.getClass(any()) } throws java.lang.RuntimeException()

        val citizenClass = viewModel.getClass(any())

        assertEquals(citizenClass, MainViewModelMockData.serverErrorDeserialized)
    }

}