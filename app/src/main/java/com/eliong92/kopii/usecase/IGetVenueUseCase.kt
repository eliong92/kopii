package com.eliong92.kopii.usecase

import io.reactivex.rxjava3.core.Single

interface IGetVenueUseCase {
    fun execute(query: String): Single<List<VenueViewObject>>
}