package com.eliong92.kopii.viewModel

import androidx.lifecycle.LiveData

interface IMainViewModel {
    fun showVenues(query: String)
    fun onDestroy()
    fun getState(): LiveData<MainViewState>
}