package com.eliong92.kopii.usecase

import com.eliong92.kopii.model.Venue
import com.eliong92.kopii.model.VenueDetail
import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import com.eliong92.kopii.repository.ILocationRepository
import com.eliong92.kopii.repository.IVenueRepository
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetVenueUseCaseTest {

    private lateinit var useCase: GetVenueUseCase

    @Mock
    lateinit var venueRepository: IVenueRepository

    @Mock
    lateinit var locationRepository: ILocationRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = GetVenueUseCase(venueRepository, locationRepository)
    }

    @Test
    fun execute_shouldMapResponseToVenueViewObject() {
        val query = "kopi"
        val latitude = 89.0
        val longitude = -19.98
        val response = VenueResponse(
            response = VenueResponse.Response(
                venues = listOf(
                    Venue(
                        id = "1",
                        name = "Kopi Takkie",
                        location = Venue.Location(
                            address = "Glodok"
                        )
                    ),
                    Venue(
                        id = "2",
                        name = "Kopi ABC",
                        location = Venue.Location(
                            address = "City Walk"
                        )
                    )
                )
            )
        )

        val expectedViewObject = listOf(
            VenueViewObject(
                name = "Kopi Takkie",
                address = "Glodok",
                rating = "8.9"
            ),
            VenueViewObject(
                name = "Kopi ABC",
                address = "City Walk",
                rating = "10.0"
            )
        )

        whenever(locationRepository.getCurrentLocation()).thenReturn(Single.just(Pair(latitude, longitude)))
        whenever(venueRepository.getVenues(query, latitude, longitude)).thenReturn(Single.just(response))
        whenever(venueRepository.getVenueDetail("1")).thenReturn(
            Single.just(
                VenueDetailResponse(
                    VenueDetailResponse.Response(
                        VenueDetail(
                            id = "1",
                            name = "Kopi Takkie",
                            rating = 8.9
                        )
                    )
                )
            )
        )
        whenever(venueRepository.getVenueDetail("2")).thenReturn(
            Single.just(
                VenueDetailResponse(
                    VenueDetailResponse.Response(
                        VenueDetail(
                            id = "2",
                            name = "Kopi ABC",
                            rating = 10.0
                        )
                    )
                )
            )
        )

        useCase.execute(query).test()
            .assertValue(expectedViewObject)
            .dispose()
    }
}