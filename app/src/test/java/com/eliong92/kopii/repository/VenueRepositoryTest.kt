package com.eliong92.kopii.repository

import com.eliong92.kopii.model.Venue
import com.eliong92.kopii.model.VenueDetail
import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import com.eliong92.kopii.network.ApiService
import org.junit.Before
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.rxjava3.core.Single
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class VenueRepositoryTest {
    private lateinit var repository: VenueRepository

    @Mock
    var apiService: ApiService = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = VenueRepository(apiService)
    }

    @Test
    fun getVenues_shouldReturnVenueResponse() {
        val query = "query"
        val expectedResponse = VenueResponse(
            response = VenueResponse.Response(
                venues = listOf(
                    Venue(
                        id = "123",
                        name = "glodok"
                    )
                )
            )
        )
        whenever(apiService.getVenues(
            clientId = "TNWXXPU1SPR50CBRH1IUVJ32U0ITMGMIARILHX2IEVLRHBML",
            clientSecret = "HFJOZ1UN1WPPSKTSVYXUM3OGRC1CYDR2LXVSDW4QDFEOY33O",
            version = "20201005",
            intent = "browse",
            radius = "10000",
            query = query,
            limit = "10",
            ll = "89.6,-8.9",
        )).thenReturn(Single.just(expectedResponse))

        repository.getVenues(
            query = query,
            latitude = 89.6,
            longitude = -8.9
        ).test()
            .assertValue(expectedResponse)
            .dispose()
    }

    @Test
    fun getVenueDetail_shouldReturnVenueDetailResponse() {
        val id = "abc"
        val expectedResponse = VenueDetailResponse(
            response = VenueDetailResponse.Response(
                VenueDetail(
                    id = "abc",
                    name = "ABCD",
                    rating = 4.5
                )
            )
        )
        whenever(apiService.getVenueDetail(
            id = id,
            clientId = "TNWXXPU1SPR50CBRH1IUVJ32U0ITMGMIARILHX2IEVLRHBML",
            clientSecret = "HFJOZ1UN1WPPSKTSVYXUM3OGRC1CYDR2LXVSDW4QDFEOY33O",
            version = "20201005",
        )).thenReturn(Single.just(expectedResponse))

        repository.getVenueDetail(id).test()
            .assertValue(expectedResponse)
            .dispose()
    }
}