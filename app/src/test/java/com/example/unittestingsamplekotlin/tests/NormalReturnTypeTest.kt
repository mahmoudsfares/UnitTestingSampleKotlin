package com.example.unittestingsamplekotlin.tests

import com.example.unittestingsamplekotlin.MainViewModel
import com.example.unittestingsamplekotlin.MockData
import com.example.unittestingsamplekotlin.model.Repository
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import kotlinx.coroutines.test.runTest

import org.junit.Assert.*
import org.mockito.kotlin.any
import java.io.IOException


@OptIn(ExperimentalCoroutinesApi::class)
class NormalReturnTypeTest {

    // mock data source
    private val repo = mockk<Repository>(relaxed = true)
    // or, you can use it this way:
    /*
    @MockK
    lateinit var repo: Repository
    @Before
    fun setUp() = MockKAnnotations.init(this)
     */

    // create an instance of the system under testing
    private val viewModel = MainViewModel(repo)

    @Test
    fun `no internet connection`() = runTest {
        // stubbing: when the method inside coEvery block is called,
        // return or throw the object outside the block
        coEvery { repo.getClass(any()) } throws IOException()

        // returned result of the tested unit
        val citizenClass = viewModel.getClassNormally(any())

        // compare between the expected result and the actual result
        assertEquals(MockData.noInternetDeserialized, citizenClass)
    }

    @Test
    fun `ok parcelable`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.ok

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(MockData.okDeserialized, citizenClass)
    }

    @Test
    fun `ok null`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.okNull

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(null, citizenClass)
    }

    @Test
    fun `ok garbage`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.okGarbage

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(MockData.unexpectedErrorDeserialized, citizenClass)
    }


    @Test
    fun `bad request parcelable`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.badRequest

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(MockData.badRequestDeserialized, citizenClass)
    }

    @Test
    fun `bad request garbage`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.badRequestGarbage

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(MockData.unexpectedErrorDeserialized, citizenClass)
    }

    @Test
    fun `bad request null`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.badRequestNull

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(null, citizenClass)
    }

    @Test
    fun `server error`() = runTest {
        coEvery { repo.getClass(any()) } throws java.lang.RuntimeException()

        val citizenClass = viewModel.getClassNormally(any())

        assertEquals(MockData.serverErrorDeserialized, citizenClass)
    }

}