package com.eliong92.kopii.viewModel

import com.eliong92.kopii.usecase.IGetVenueUseCase
import dagger.Module
import dagger.Provides

@Module
class MainViewModelModule {

    @Provides
    fun provideViewModelProvider(
        useCase: IGetVenueUseCase
    ): MainViewModelProvider {
        return MainViewModelProvider(useCase)
    }
}