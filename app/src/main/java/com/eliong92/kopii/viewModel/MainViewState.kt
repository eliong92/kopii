package com.eliong92.kopii.viewModel

import com.eliong92.kopii.usecase.VenueViewObject

sealed class MainViewState {
    object OnLoading: MainViewState()
    object OnError: MainViewState()
    data class OnSuccess(val items: List<VenueViewObject>): MainViewState()
}