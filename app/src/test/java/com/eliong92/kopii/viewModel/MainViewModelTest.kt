package com.eliong92.kopii.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eliong92.kopii.CoroutineTestRule
import com.eliong92.kopii.usecase.IGetVenueUseCase
import com.eliong92.kopii.usecase.VenueViewObject
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var viewStateObserver: Observer<MainViewState>

    @Mock
    lateinit var useCase: IGetVenueUseCase

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(useCase)
        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun showVenues_whenLoadSuccess_shouldShowVenueList() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            val venues = listOf(
                VenueViewObject("abc", "warung", "10.0")
            )
            whenever(useCase.execute("kopi")).thenReturn(venues)

            viewModel.showVenues()

            val inOrder = inOrder(viewStateObserver)
            inOrder.verify(viewStateObserver).onChanged(MainViewState.OnLoading)
            inOrder.verify(viewStateObserver).onChanged(MainViewState.OnSuccess(venues))
        }
    }

    @Test
    fun showVenues_whenLoadError_shouldShowError() {
        coroutineTestRule.testDispatcher.runBlockingTest {
            whenever(useCase.execute("kopi")).thenThrow(
                HttpException(Response.error<List<VenueViewObject>>(500, "Error".toResponseBody()))
            )

            viewModel.showVenues()

            val inOrder = inOrder(viewStateObserver)
            inOrder.verify(viewStateObserver).onChanged(MainViewState.OnLoading)
            inOrder.verify(viewStateObserver).onChanged(MainViewState.OnError)
        }
    }
}