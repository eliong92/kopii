package com.eliong92.kopii.usecase

import com.eliong92.kopii.repository.IVenueRepository

class GetVenueUseCase(
    private val venueRepository: IVenueRepository
): IGetVenueUseCase {
    override suspend fun execute(query: String): List<VenueViewObject> {
//        return withContext(Dispatchers.IO) {
//            venueRepository.getVenues(query).response.venues.map { venue ->
//                Pair(venue, async {
//                    venueRepository.getVenueDetail(venue.id)
//                })
//
//            }.map {
//                Pair(it.first, it.second.await())
//            }.map {
//                VenueViewObject(
//                    name = it.first.name,
//                    address = it.first.location.address,
//                    rating = it.second.response.venue.rating.toString()
//                )
//            }
//        }

        return venueRepository.getVenues(query).response.venues.map {
            val detail = venueRepository.getVenueDetail(it.id)
            VenueViewObject(
                name = it.name,
                address = it.location.address,
                rating = detail.response.venue.rating.toString()
            )
        }
    }
}