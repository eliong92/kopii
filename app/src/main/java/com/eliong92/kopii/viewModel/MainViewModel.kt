package com.eliong92.kopii.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliong92.kopii.usecase.IGetVenueUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(
    private val useCase: IGetVenueUseCase
) : ViewModel() {
    val viewState = MutableLiveData<MainViewState>()

    fun showVenues() {
        viewModelScope.launch {
            viewState.value = MainViewState.OnLoading
            try {
                val venues = useCase.execute("kopi")
                viewState.value = MainViewState.OnSuccess(venues)
            } catch (e: HttpException) {
                viewState.value = MainViewState.OnError
            }
        }
    }
}