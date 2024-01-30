package com.silosoft.technologies.dvtweatherapp.repository

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.data
import com.silosoft.technologies.dvtweatherapp.data.implementations.NearbySearchRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Geometry
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Location
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.NearbySearchResponse
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Northeast
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.PlusCode
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Southwest
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Viewport
import com.silosoft.technologies.dvtweatherapp.domain.api.PlacesApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Result as ModelResult

class NearbySearchRepositoryImplTest {
    private lateinit var repository: NearbySearchRepositoryImpl
    private val mockPlacesApi = mockk<PlacesApi>()
    private val mockDataStoreRepository = mockk<DataStoreRepository>()

    @BeforeEach
    fun setup() {
        repository = NearbySearchRepositoryImpl(
            mockPlacesApi,
            mockDataStoreRepository
        )
    }

    @Test
    fun `getNearbyRestaurants fetches from API and stores data on success`() = runTest {
        // Arrange
        val mockResponse = NearbySearchResponse(
            emptyList(),
            "",
            listOf(
                ModelResult(
                    "",
                    Geometry(
                        Location(0.0, 0.0), Viewport(Northeast(0.0, 0.0), Southwest(0.0, 0.0))
                    ),
                    "",
                    "",
                    "",
                    "ShopName",
                    permanentlyClosed = false,
                    emptyList(),
                    "",
                    PlusCode("", ""),
                    4.55,
                    "",
                    "",
                    emptyList(),
                    userRatingsTotal = 200,
                    vicinity = "address"
                )
            ),
            "200"
        )

        coEvery {
            mockPlacesApi.getNearbyRestaurants(
                any(),
                any(),
                any()
            )
        } returns Response.success(
            mockResponse
        )
        coEvery { mockDataStoreRepository.storeNearbySearch(any()) } just Runs

        // Act
        val result = repository.getNearbyRestaurants("location")

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockResponse, result.data)
        coVerify { mockDataStoreRepository.storeNearbySearch(mockResponse) }
    }

    @Test
    fun `getNearbyRestaurants returns error when API response body is null`() = runTest {
        // Arrange
        val apiResponse: Response<NearbySearchResponse> = mockk(relaxed = true)

        coEvery { apiResponse.isSuccessful } returns true
        coEvery { apiResponse.body() } returns null
        coEvery { mockPlacesApi.getNearbyRestaurants(any(), any(), any()) } returns apiResponse

        // Act
        val result = repository.getNearbyRestaurants("location")

        // Assert
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getNearbyRestaurants accesses dataStoreRepository and returns success if API fails and data available`() =
        runTest {
            // Arrange
            val exception = IOException("Network error")
            val lastStoredNearbySearchResponse: NearbySearchResponse = mockk()
            coEvery { mockPlacesApi.getNearbyRestaurants(any(), any(), any()) } throws exception
            coEvery { mockDataStoreRepository.getNearbySearch() } returns flowOf(
                lastStoredNearbySearchResponse
            )

            // Act
            val result = repository.getNearbyRestaurants("testLocation")

            // Assert
            assertTrue(result is Result.Success)
            assertEquals(lastStoredNearbySearchResponse, (result as Result.Success).data)
        }

    @Test
    fun `getNearbyRestaurants returns error when both API call fails and no local data available`() =
        runTest {
            // Arrange
            val exception = IOException("Network error")
            coEvery { mockPlacesApi.getNearbyRestaurants(any(), any(), any()) } throws exception
            coEvery { mockDataStoreRepository.getNearbySearch() } returns flowOf(null)

            // Act
            val result = repository.getNearbyRestaurants("testLocation")

            // Assert
            assertTrue(result is Result.Error)
        }
}
