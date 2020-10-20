package com.eliong92.kopii.repository

import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import io.reactivex.rxjava3.core.Single

interface IVenueRepository {
    fun getVenues(
        query: String,
        latitude: Double,
        longitude: Double
    ): Single<VenueResponse>
    fun getVenueDetail(id: String): Single<VenueDetailResponse>
}