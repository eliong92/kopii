package com.eliong92.kopii.di

import com.eliong92.kopii.repository.ILocationRepository
import com.eliong92.kopii.repository.IVenueRepository
import com.eliong92.kopii.usecase.GetVenueUseCase
import com.eliong92.kopii.usecase.IGetVenueUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideGetVenueUseCase(repo: IVenueRepository, locationRepo: ILocationRepository): IGetVenueUseCase {
       return GetVenueUseCase(repo, locationRepo)
    }
}