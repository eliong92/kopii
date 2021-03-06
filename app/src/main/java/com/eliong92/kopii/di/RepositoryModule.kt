package com.eliong92.kopii.di

import android.content.Context
import com.eliong92.kopii.network.ApiService
import com.eliong92.kopii.repository.ILocationRepository
import com.eliong92.kopii.repository.IVenueRepository
import com.eliong92.kopii.repository.LocationRepository
import com.eliong92.kopii.repository.VenueRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideVenueRepository(apiService: ApiService): IVenueRepository {
       return VenueRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideLocationRepository(context: Context): ILocationRepository {
        return LocationRepository(context)
    }
}