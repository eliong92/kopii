package com.eliong92.kopii.model

import com.google.gson.annotations.SerializedName

data class VenueDetailResponse(
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("venue")
        val venue: VenueDetail = VenueDetail()
    )
}