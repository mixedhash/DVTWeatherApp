package com.silosoft.technologies.dvtweatherapp.repository

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.implementations.OpenWeatherRepositoryImpl
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.City
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Clouds
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Coord
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Main
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Sys
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Wind
import com.silosoft.technologies.dvtweatherapp.domain.api.OpenWeatherApi
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
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Coord as ForecastCoord

class OpenWeatherRepositoryImplTest {
    private lateinit var repository: OpenWeatherRepositoryImpl
    private val mockOpenWeatherApi = mockk<OpenWeatherApi>()
    private val mockDataStoreRepository = mockk<DataStoreRepository>()

    @BeforeEach
    fun setup() {
        repository = OpenWeatherRepositoryImpl(
            mockOpenWeatherApi,
            mockDataStoreRepository
        )
    }

    @Test
    fun `getWeather fetches from API and stores data on success`() = runTest {
        // Arrange
        val mockResponse = WeatherResponse(
            "test",
            Clouds(0),
            1,
            Coord(1.0, 3.0),
            1,
            1,
            Main(1.0, 1, 1, 1.0, 1.0, 1.0),
            "test",
            Sys("test", 1, 1, 1, 1),
            1,
            1,
            listOf(),
            Wind(1, 2.0)
        )

        coEvery { mockOpenWeatherApi.getWeather(any(), any(), any()) } returns Response.success(
            mockResponse
        )
        coEvery { mockDataStoreRepository.storeWeather(any()) } just Runs

        // Act
        val result = repository.getWeather("lat", "lon")

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockResponse, (result as Result.Success).data)
        coVerify { mockDataStoreRepository.storeWeather(mockResponse) }
    }

    @Test
    fun `getWeather returns error when API response body is null`() = runTest {
        // Arrange
        val apiResponse: Response<WeatherResponse> = mockk(relaxed = true)

        coEvery { apiResponse.isSuccessful } returns true
        coEvery { apiResponse.body() } returns null
        coEvery { mockOpenWeatherApi.getWeather(any(), any(), any()) } returns apiResponse

        // Act
        val result = repository.getWeather("lat", "lon")

        // Assert
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getWeather accesses dataStoreRepository and returns success if API fails and data available`() =
        runTest {
            // Arrange
            val exception = IOException("Network error")
            val lastStoredWeatherResponse: WeatherResponse = mockk()
            coEvery { mockOpenWeatherApi.getWeather(any(), any(), any()) } throws exception
            coEvery { mockDataStoreRepository.getWeather() } returns flowOf(
                lastStoredWeatherResponse
            )

            // Act
            val result = repository.getWeather("lat", "lon")

            // Assert
            assertTrue(result is Result.Success)
            assertEquals(lastStoredWeatherResponse, (result as Result.Success).data)
        }

    @Test
    fun `getWeather returns error when both API call fails or throws exception and no local data available`() =
        runTest {
            // Arrange
            val exception = IOException("Network error")
            coEvery { mockOpenWeatherApi.getWeather(any(), any(), any()) } throws exception
            coEvery { mockDataStoreRepository.getWeather() } returns flowOf(null)

            // Act
            val result = repository.getWeather("lat", "lon")

            // Assert
            assertTrue(result is Result.Error)
        }

    @Test
    fun `getForecast fetches from API and stores data on success`() = runTest {
        // Arrange
        val mockResponse = ForecastResponse(
            City(
                ForecastCoord(0.0, 0.0),
                "RO",
                1,
                "test",
                100,
                1,
                1,
                100
            ),
            111,
            "123",
            emptyList(),
            200
        )

        coEvery { mockOpenWeatherApi.getForecast(any(), any(), any()) } returns Response.success(
            mockResponse
        )
        coEvery { mockDataStoreRepository.storeForecast(any()) } just Runs

        // Act
        val result = repository.getForecast("lat", "lon")

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockResponse, (result as Result.Success).data)
        coVerify { mockDataStoreRepository.storeForecast(mockResponse) }
    }

    @Test
    fun `getForecast returns error when API response body is null`() = runTest {
        // Arrange
        val apiResponse: Response<ForecastResponse> = mockk(relaxed = true)

        coEvery { apiResponse.isSuccessful } returns true
        coEvery { apiResponse.body() } returns null
        coEvery { mockOpenWeatherApi.getForecast(any(), any(), any()) } returns apiResponse

        // Act
        val result = repository.getForecast("lat", "lon")

        // Assert
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getForecast accesses dataStoreRepository and returns success if data available if API throws exception`() =
        runTest {
            // Arrange
            val exception = IOException("Network error")
            val lastStoredForecastResponse: ForecastResponse = mockk()
            coEvery { mockOpenWeatherApi.getForecast(any(), any(), any()) } throws exception
            coEvery { mockDataStoreRepository.getForecast() } returns flowOf(
                lastStoredForecastResponse
            )

            // Act
            val result = repository.getForecast("lat", "lon")

            // Assert
            assertTrue(result is Result.Success)
            assertEquals(lastStoredForecastResponse, (result as Result.Success).data)
        }

    @Test
    fun `getForecast returns error when both API call fails or throws exception and no local data available`() =
        runTest {
            // Arrange
            val exception = IOException("Network error")
            coEvery { mockOpenWeatherApi.getForecast(any(), any(), any()) } throws exception
            coEvery { mockDataStoreRepository.getForecast() } returns flowOf(null)

            // Act
            val result = repository.getForecast("lat", "lon")

            // Assert
            assertTrue(result is Result.Error)
        }
}
