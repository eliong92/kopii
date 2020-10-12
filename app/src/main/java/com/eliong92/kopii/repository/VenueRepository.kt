package com.eliong92.kopii.repository

import com.eliong92.kopii.Constant
import com.eliong92.kopii.model.VenueDetail
import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import com.eliong92.kopii.network.ApiService

class VenueRepository(
    private val apiService: ApiService
): IVenueRepository {
    override suspend fun getVenues(query: String): VenueResponse {
        return apiService.getVenues(
            clientId = Constant.CLIENT_ID,
            clientSecret = Constant.CLIENT_SECRET,
            version = Constant.VERSION,
            near = "jakarta",
            intent = "browse",
            radius = "10000",
            query = query,
            limit = "10"
        )
    }

    override suspend fun getVenueDetail(id: String): VenueDetailResponse {
        return apiService.getVenueDetail(
            id = id,
            clientId = Constant.CLIENT_ID,
            clientSecret = Constant.CLIENT_SECRET,
            version = Constant.VERSION
        )
    }
}