package com.eliong92.kopii.model

import com.google.gson.annotations.SerializedName

data class VenueResponse(
    @SerializedName("response")
    val response: Response = Response()
) {
    data class Response(
        @SerializedName("venues")
        val venues: List<Venue> = listOf()
    )
}