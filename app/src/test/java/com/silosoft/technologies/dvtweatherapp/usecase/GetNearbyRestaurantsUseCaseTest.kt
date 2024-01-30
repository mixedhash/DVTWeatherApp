package com.silosoft.technologies.dvtweatherapp.usecase

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Geometry
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Location
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.NearbySearchResponse
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Northeast
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.PlusCode
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Southwest
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Viewport
import com.silosoft.technologies.dvtweatherapp.domain.repository.NearbySearchRepository
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetNearbyRestaurantsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.RuntimeException
import com.silosoft.technologies.dvtweatherapp.data.response.nearbysearch.Result as NearbysearchResult

class GetNearbyRestaurantsUseCaseTest {
    private lateinit var useCase: GetNearbyRestaurantsUseCase
    private val mockRepo = mockk<NearbySearchRepository>()

    @BeforeEach
    fun setup() {
        useCase = GetNearbyRestaurantsUseCase(mockRepo)
    }

    @Test
    fun `execute returns WeatherUiModel on success`() = runTest {
        // Arrange
        val mockResponse = NearbySearchResponse(
            emptyList(),
            "",
            listOf(
                NearbysearchResult(
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
        coEvery { mockRepo.getNearbyRestaurants(any()) } returns Result.Success(mockResponse)

        // Act
        val result = useCase.execute("location")

        // Assert
        assertNotNull(result)
        assertEquals(mockResponse.results.first().name, result?.restaurants?.first()?.name)
        assertEquals(mockResponse.results.first().rating, result?.restaurants?.first()?.overallRating)
        assertEquals(mockResponse.results.first().userRatingsTotal, result?.restaurants?.first()?.userRatings)
        assertEquals(mockResponse.results.first().vicinity, result?.restaurants?.first()?.vicinity)
    }

    @Test
    fun `execute returns null and logs error on failure`() = runTest {
        // Arrange
        val fakeError = RuntimeException("Error")
        val fakeResult = Result.Error(fakeError)
        coEvery { mockRepo.getNearbyRestaurants(any()) } returns fakeResult

        // Act
        val result = useCase.execute("location")

        // Assert
        assertNull(result)
    }
}
