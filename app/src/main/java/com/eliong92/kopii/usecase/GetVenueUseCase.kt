package com.eliong92.kopii.usecase

import com.eliong92.kopii.repository.ILocationRepository
import com.eliong92.kopii.repository.IVenueRepository
import io.reactivex.rxjava3.core.Single

class GetVenueUseCase(
    private val venueRepository: IVenueRepository,
    private val locationRepository: ILocationRepository
): IGetVenueUseCase {
    override fun execute(query: String): Single<List<VenueViewObject>> {
        return locationRepository.getCurrentLocation().flatMap { ll ->
            venueRepository.getVenues(query, ll.first, ll.second)
                .flattenAsObservable { it.response.venues }
                .flatMapSingle { venue ->
                    venueRepository.getVenueDetail(venue.id).map { detail ->
                        VenueViewObject(
                            name = venue.name,
                            address = venue.location.address,
                            rating = detail.response.venue.rating.toString()
                        )
                    }
                }
                .toList()
        }
    }
}