package com.eliong92.kopii.repository

import com.eliong92.kopii.model.Venue
import com.eliong92.kopii.model.VenueDetail
import com.eliong92.kopii.model.VenueDetailResponse
import com.eliong92.kopii.model.VenueResponse
import com.eliong92.kopii.network.ApiService
import org.junit.Before
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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
    fun getVenues_shouldReturnVenueResponse() = runBlocking {
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
            near = "jakarta",
            intent = "browse",
            radius = "10000",
            query = query,
            limit = "10"
        )).thenReturn(expectedResponse)

        val response = repository.getVenues(query)

        assertEquals(expectedResponse, response)
    }

    @Test
    fun getVenueDetail_shouldReturnVenueDetailResponse() = runBlocking {
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
        )).thenReturn(expectedResponse)

        val response = repository.getVenueDetail(id)

        assertEquals(expectedResponse, response)
    }
}