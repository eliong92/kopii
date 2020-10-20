package com.eliong92.kopii.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.eliong92.kopii.network.IScheduleProvider
import com.eliong92.kopii.usecase.IGetVenueUseCase
import com.eliong92.kopii.usecase.VenueViewObject
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Observable.just
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.TestScheduler
import junit.framework.Assert.assertEquals
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response

class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel

    @Mock
    lateinit var viewStateObserver: Observer<MainViewState>

    @Mock
    lateinit var useCase: IGetVenueUseCase

    @Mock
    lateinit var scheduleProvider: IScheduleProvider

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(useCase, scheduleProvider)
        viewModel.viewState.observeForever(viewStateObserver)
    }

    @Test
    fun showVenues_whenLoadSuccess_shouldShowVenueList() {
        val testScheduler = TestScheduler()
        whenever(scheduleProvider.io()).thenReturn(testScheduler)
        whenever(scheduleProvider.mainThread()).thenReturn(testScheduler)

        val venues = listOf(
            VenueViewObject("abc", "warung", "10.0")
        )
        whenever(useCase.execute("kopi")).thenReturn(Single.just(venues))

        viewModel.showVenues()
        testScheduler.triggerActions()

        val inOrder = inOrder(viewStateObserver)
        inOrder.verify(viewStateObserver).onChanged(MainViewState.OnLoading)
        inOrder.verify(viewStateObserver).onChanged(MainViewState.OnSuccess(venues))
        assertEquals(1, viewModel.compositeDisposable.size())
    }

    @Test
    fun showVenues_whenLoadError_shouldShowError() {
        val testScheduler = TestScheduler()
        whenever(scheduleProvider.io()).thenReturn(testScheduler)
        whenever(scheduleProvider.mainThread()).thenReturn(testScheduler)

        whenever(useCase.execute("kopi")).thenReturn(
            Single.error(HttpException(Response.error<List<VenueViewObject>>(500, "Error".toResponseBody())))
        )

        viewModel.showVenues()
        testScheduler.triggerActions()

        val inOrder = inOrder(viewStateObserver)
        inOrder.verify(viewStateObserver).onChanged(MainViewState.OnLoading)
        inOrder.verify(viewStateObserver).onChanged(MainViewState.OnError)
    }

    @Test
    fun onDestroy_shouldClearCompositeDisposable() {
        val obs = just("a").subscribe()
        viewModel.compositeDisposable.add(obs)
        viewModel.onDestroy()
        assertEquals(0, viewModel.compositeDisposable.size())
    }
}