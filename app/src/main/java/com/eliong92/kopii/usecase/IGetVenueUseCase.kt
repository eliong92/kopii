package com.eliong92.kopii.usecase

interface IGetVenueUseCase {
    suspend fun execute(query: String): List<VenueViewObject>
}