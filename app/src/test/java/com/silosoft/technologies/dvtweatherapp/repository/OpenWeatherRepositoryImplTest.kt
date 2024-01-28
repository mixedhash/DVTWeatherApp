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
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.IOException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.Response
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Coord as ForecastCoord

class OpenWeatherRepositoryImplTest {
    private lateinit var openWeatherApi: OpenWeatherApi
    private lateinit var openWeatherRepository: OpenWeatherRepositoryImpl

    @BeforeEach
    fun setup() {
        openWeatherApi = mockk()
        openWeatherRepository = OpenWeatherRepositoryImpl(openWeatherApi)
    }


    @Test
    fun `getWeather returns success on successful API response`() = runTest {
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

        coEvery { openWeatherApi.getWeather(any(), any(), any()) } returns Response.success(
            mockResponse
        )

        // Act
        val result = openWeatherRepository.getWeather("lat", "lon")

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockResponse, (result as Result.Success).data)
    }

    @Test
    fun `getWeather returns error on API HTTP error`() = runTest {
        // Arrange
        val errorResponseBody = "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        val errorResponse: Response<WeatherResponse> = Response.error(404, errorResponseBody)

        coEvery { openWeatherApi.getWeather(any(), any(), any()) } returns errorResponse

        // Act
        val result = openWeatherRepository.getWeather("lat", "lon")

        // Assert
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getWeather returns error on API exception`() = runTest {
        // Arrange
        val exception = IOException("Network error")
        coEvery { openWeatherApi.getWeather(any(), any(), any()) } throws exception

        // Act
        val result = openWeatherRepository.getWeather("lat", "lon")

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }

    @Test
    fun `getForecast returns success on successful API response`() = runTest {
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
        coEvery { openWeatherApi.getForecast(any(), any(), any()) } returns Response.success(
            mockResponse
        )

        // Act
        val result = openWeatherRepository.getForecast("lat", "lon")

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(mockResponse, (result as Result.Success).data)
    }

    @Test
    fun `getForecast returns error on API HTTP error`() = runTest {
        // Arrange
        val errorResponseBody = "Not Found".toResponseBody("text/plain".toMediaTypeOrNull())
        val errorResponse: Response<ForecastResponse> = Response.error(404, errorResponseBody)

        coEvery { openWeatherApi.getForecast(any(), any(), any()) } returns errorResponse

        // Act
        val result = openWeatherRepository.getForecast("lat", "lon")

        // Assert
        assertTrue(result is Result.Error)
    }

    @Test
    fun `getForecast returns error on API exception`() = runTest {
        // Arrange
        val exception = IOException("Network error")
        coEvery { openWeatherApi.getForecast(any(), any(), any()) } throws exception

        // Act
        val result = openWeatherRepository.getForecast("lat", "lon")

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(exception, (result as Result.Error).error)
    }
}
