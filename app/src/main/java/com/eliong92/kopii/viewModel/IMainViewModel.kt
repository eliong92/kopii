package com.eliong92.kopii.viewModel

import androidx.lifecycle.LiveData

interface IMainViewModel {
    fun showVenues()
    fun onDestroy()
    fun getState(): LiveData<MainViewState>
}