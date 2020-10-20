package com.eliong92.kopii.viewModel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eliong92.kopii.network.IScheduleProvider
import com.eliong92.kopii.usecase.IGetVenueUseCase
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MainViewModel(
    private val useCase: IGetVenueUseCase,
    private val scheduleProvider: IScheduleProvider
) : ViewModel(), IMainViewModel {
    val viewState = MutableLiveData<MainViewState>()
    @VisibleForTesting val compositeDisposable = CompositeDisposable()

    override fun showVenues(query: String) {
        compositeDisposable.add(useCase.execute(query)
            .subscribeOn(scheduleProvider.io())
            .observeOn(scheduleProvider.mainThread())
            .doOnSubscribe { viewState.value = MainViewState.OnLoading }
            .subscribe({ venues ->
                viewState.value = MainViewState.OnSuccess(venues)
            }, {
                viewState.value = MainViewState.OnError
            })
        )
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun getState(): LiveData<MainViewState> {
        return viewState
    }
}