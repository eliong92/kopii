package com.eliong92.kopii.repository

import com.eliong92.kopii.Constant
import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import com.eliong92.kopii.network.ApiService
import io.reactivex.rxjava3.core.Single

class VenueRepository(
    private val apiService: ApiService
): IVenueRepository {
    override fun getVenues(
        query: String,
        latitude: Double,
        longitude: Double
    ): Single<VenueResponse> {
        return apiService.getVenues(
            clientId = Constant.CLIENT_ID,
            clientSecret = Constant.CLIENT_SECRET,
            version = Constant.VERSION,
            intent = "browse",
            radius = "10000",
            query = query,
            limit = "10",
            ll = "$latitude,$longitude",
        )
    }

    override fun getVenueDetail(id: String): Single<VenueDetailResponse> {
        return apiService.getVenueDetail(
            id = id,
            clientId = Constant.CLIENT_ID,
            clientSecret = Constant.CLIENT_SECRET,
            version = Constant.VERSION
        )
    }
}