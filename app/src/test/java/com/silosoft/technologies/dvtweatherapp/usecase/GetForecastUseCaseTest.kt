package com.silosoft.technologies.dvtweatherapp.usecase

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.City
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Clouds
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Coord
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Day
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Main
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Rain
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Snow
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Sys
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Weather
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.Wind
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetForecastUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

class GetForecastUseCaseTest {
    private lateinit var useCase: GetForecastUseCase
    private val mockRepo = mockk<OpenWeatherRepository>()

    @BeforeEach
    fun setup() {
        useCase = GetForecastUseCase(mockRepo)
    }

    @Test
    fun `execute aggregates data successfully`() = runTest {
        // Arrange
        val fakeResponse = Result.Success(
            ForecastResponse(
                City(
                    Coord(1.0, 2.0),
                    "Romania",
                    1,
                    "Cluj-Napoca",
                    500000,
                    1,
                    1,
                    1
                ),
                111,
                "123",
                listOf(
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 12:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clouds"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    ),
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 15:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clouds"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    ),
                    Day(
                        Clouds(1),
                        1,
                        "2024-02-30 15:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clouds"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    )
                ),
                200
            )
        )
        coEvery { mockRepo.getForecast(any(), any()) } returns fakeResponse

        // Act
        val result = useCase.execute("lat", "lon")

        // Assert
        assertNotNull(result)
        assertEquals(2, result?.forecast?.size)
    }

    @Test
    fun `execute handles multiple weather types correctly`() = runTest {
        // Arrange
        val fakeResponse = Result.Success(
            ForecastResponse(
                City(
                    Coord(1.0, 2.0),
                    "Romania",
                    1,
                    "Cluj-Napoca",
                    500000,
                    1,
                    1,
                    1
                ),
                111,
                "123",
                listOf(
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 12:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Rain"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    ),
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 15:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clouds"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    ),
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 15:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clouds"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    )
                ),
                200
            )
        )
        coEvery { mockRepo.getForecast(any(), any()) } returns fakeResponse

        // Act
        val result = useCase.execute("lat", "lon")

        assertNotNull(result)
        assertEquals("Clouds", result?.forecast?.first()?.weatherType)
    }


    @Test
    fun `execute handles unique weather type correctly`() = runTest {
        // Arrange
        val fakeResponse = Result.Success(
            ForecastResponse(
                City(
                    Coord(1.0, 2.0),
                    "Romania",
                    1,
                    "Cluj-Napoca",
                    500000,
                    1,
                    1,
                    1
                ),
                111,
                "123",
                listOf(
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 12:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Rain"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    ),
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 15:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clouds"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    ),
                    Day(
                        Clouds(1),
                        1,
                        "2024-01-30 15:00:00",
                        Main(
                            21.1,
                            1,
                            1,
                            1,
                            1,
                            21.1,
                            21.1,
                            22.1,
                            19.2
                        ),
                        2.0,
                        Rain(2.0),
                        Snow(2.0),
                        Sys("test"),
                        1,
                        listOf(
                            Weather(
                                "clouds",
                                "test",
                                1,
                                "Clear"
                            )
                        ),
                        Wind(1, 2.0, 2.0)
                    )
                ),
                200
            )
        )
        coEvery { mockRepo.getForecast(any(), any()) } returns fakeResponse

        // Act
        val result = useCase.execute("lat", "lon")

        assertNotNull(result)
        assertEquals("Rain", result?.forecast?.first()?.weatherType)
    }

    @Test
    fun `execute returns null and logs error on failure`() = runTest {
        // Arrange
        val fakeException = IOException("Error getting the data from repo!")
        coEvery { mockRepo.getForecast(any(), any()) } returns Result.Error(fakeException)

        // Act
        val result = useCase.execute("lat", "lon")

        // Assert
        assertNull(result)
    }
}
