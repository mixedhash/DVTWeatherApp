package com.silosoft.technologies.dvtweatherapp.usecase

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Coord
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Clouds
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Main
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Sys
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Weather
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.Wind
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetWeatherUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetWeatherUseCaseTest {
    private lateinit var useCase: GetWeatherUseCase
    private val mockRepo = mockk<OpenWeatherRepository>()

    @BeforeEach
    fun setup() {
        useCase = GetWeatherUseCase(mockRepo)
    }

    @Test
    fun `execute returns WeatherUiModel on success`() = runTest {
        // Arrange
        val fakeResponse = Result.Success(
            WeatherResponse(
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
                listOf(
                    Weather(
                        "Clouds",
                        "Clouds",
                        1,
                        "test"
                    )
                ),
                Wind(1, 2.0)
            )
        )
        coEvery { mockRepo.getWeather(any(), any()) } returns fakeResponse

        // Act
        val result = useCase.execute("lat", "lon")

        // Assert
        assertNotNull(result)
        assertEquals(fakeResponse.data.main.temp.toInt(), result?.currentTemp)
        assertEquals(fakeResponse.data.weather[0].main, result?.weatherType)
        assertEquals(fakeResponse.data.name, result?.locationName)
        assertEquals(fakeResponse.data.main.tempMin.toInt(), result?.minTemp)
        assertEquals(fakeResponse.data.main.tempMax.toInt(), result?.maxTemp)
    }

    @Test
    fun `execute returns null and logs error on failure`() = runTest {
        // Arrange
        val fakeError = RuntimeException("Error")
        val fakeResult = Result.Error(fakeError)
        coEvery { mockRepo.getWeather(any(), any()) } returns fakeResult

        // Act
        val result = useCase.execute("lat", "lon")

        // Assert
        assertNull(result)
    }
}
