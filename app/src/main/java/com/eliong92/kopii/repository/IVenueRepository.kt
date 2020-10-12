package com.eliong92.kopii.repository

import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse

interface IVenueRepository {
    suspend fun getVenues(query: String): VenueResponse
    suspend fun getVenueDetail(id: String): VenueDetailResponse
}