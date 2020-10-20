package com.eliong92.kopii.network

import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("v2/venues/search")
    fun getVenues(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: String,
        @Query("intent") intent: String,
        @Query("radius") radius: String,
        @Query("query") query: String,
        @Query("limit") limit: String,
        @Query("near") near: String? = null,
        @Query("ll") ll: String? = null
    ): Single<VenueResponse>

    @GET("v2/venues/{id}")
    fun getVenueDetail(
        @Path("id") id: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("v") version: String,
    ): Single<VenueDetailResponse>
}