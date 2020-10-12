package com.eliong92.kopii.model

import com.google.gson.annotations.SerializedName

data class VenueDetail(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("rating")
    val rating: Double = 0.0
)