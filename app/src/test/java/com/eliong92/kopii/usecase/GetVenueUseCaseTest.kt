package com.eliong92.kopii.usecase

import com.eliong92.kopii.model.Venue
import com.eliong92.kopii.model.VenueDetail
import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import com.eliong92.kopii.repository.IVenueRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetVenueUseCaseTest {

    private lateinit var useCase: GetVenueUseCase

    @Mock
    lateinit var venueRepository: IVenueRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        useCase = GetVenueUseCase(venueRepository)
    }

    @Test
    fun execute_shouldMapResponseToVenueViewObject() = runBlocking {
        val query = "kopi"
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

        whenever(venueRepository.getVenues(query)).thenReturn(response)
        whenever(venueRepository.getVenueDetail("1")).thenReturn(
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
        whenever(venueRepository.getVenueDetail("2")).thenReturn(
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

        val venues = useCase.execute(query)

        assertEquals(expectedViewObject, venues)
    }
}