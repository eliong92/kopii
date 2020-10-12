package com.eliong92.kopii.model

import com.google.gson.annotations.SerializedName

data class Venue(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("location")
    val location: Location = Location()
) {
    data class Location(
        @SerializedName("address")
        val address: String = ""
    )
}