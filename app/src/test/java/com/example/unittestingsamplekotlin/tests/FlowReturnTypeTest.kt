package com.example.unittestingsamplekotlin.tests
import com.example.unittestingsamplekotlin.MainViewModel
import com.example.unittestingsamplekotlin.MockData
import com.example.unittestingsamplekotlin.model.Repository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.any
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class FlowReturnTypeTest {

    // mock data source
    private val repo = mockk<Repository>(relaxed = true)
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
        // stubbing: when the method inside coEvery block is called,
        // return or throw the object outside the block
        coEvery { repo.getClass(any()) } throws IOException()

        // will be used to hold the emitted results of the flow
        val values = mutableListOf<String?>()

        // stored in a variable to be cancelled later
        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(MockData.noInternetDeserialized, values[1])

        collectJob.cancel()
    }


    @Test
    fun `ok parcelable`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.ok

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(MockData.okDeserialized, values[1])

        collectJob.cancel()
    }


    @Test
    fun `ok null`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.okNull

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(null, values[1])

        collectJob.cancel()
    }

    @Test
    fun `ok garbage`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.okGarbage

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(MockData.unexpectedErrorDeserialized, values[1])

        collectJob.cancel()
    }


    @Test
    fun `bad request parcelable`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.badRequest

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(MockData.badRequestDeserialized, values[1])

        collectJob.cancel()
    }

    @Test
    fun `bad request garbage`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.badRequestGarbage

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(MockData.unexpectedErrorDeserialized, values[1])

        collectJob.cancel()
    }

    @Test
    fun `bad request null`() = runTest {
        coEvery { repo.getClass(any()) } returns MockData.badRequestNull

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(null, values[1])

        collectJob.cancel()
    }

    @Test
    fun `server error`() = runTest {
        coEvery { repo.getClass(any()) } throws java.lang.RuntimeException()

        val values = mutableListOf<String?>()

        val collectJob = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.getClassFlow(any()).toList(values)
        }

        Assert.assertEquals("Loading..", values[0])
        Assert.assertEquals(MockData.serverErrorDeserialized, values[1])

        collectJob.cancel()
    }
}