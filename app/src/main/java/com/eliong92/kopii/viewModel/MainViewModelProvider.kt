package com.eliong92.kopii.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eliong92.kopii.usecase.IGetVenueUseCase
import javax.inject.Inject

class MainViewModelProvider @Inject constructor(
    private val useCase: IGetVenueUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(useCase) as T
    }
}