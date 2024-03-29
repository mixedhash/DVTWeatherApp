package com.silosoft.technologies.dvtweatherapp

import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastDay
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.NearbyRestaurantsUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.Restaurant
import com.silosoft.technologies.dvtweatherapp.domain.model.WeatherUiModel
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetForecastUseCase
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetWeatherUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest {
    private lateinit var viewModel: MainViewModel
    private val mockRepo = mockk<LocationRepository>()
    private val mockGetWeatherUseCase = mockk<GetWeatherUseCase>()
    private val mockGetForecastUseCase = mockk<GetForecastUseCase>()
    private val mockGetNearbyRestaurantsUseCase = mockk<GetNearbyRestaurantsUseCase>()
    private val mockDataStoreRepository = mockk<DataStoreRepository>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeEach
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        viewModel = MainViewModel(
            mockRepo,
            mockGetWeatherUseCase,
            mockGetForecastUseCase,
            mockGetNearbyRestaurantsUseCase,
            mockDataStoreRepository
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterEach
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `execute onEvent for OnFetchLocation with success`() = runTest {
        // Arrange
        coEvery { mockRepo.getCurrentLocation() } returns Pair(50.1, 50.6)

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchLocation)

        // Assert
        coVerify { mockRepo.getCurrentLocation() }
        assertEquals(50.1, viewModel.locationState.value?.first)
        assertEquals(50.6, viewModel.locationState.value?.second)
    }

    @Test
    fun `execute onEvent for OnFetchLocation with failure`() = runTest {
        // Arrange
        coEvery { mockRepo.getCurrentLocation() } returns null

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchLocation)

        // Assert
        coVerify { mockRepo.getCurrentLocation() }
        assertNull(viewModel.locationState.value)
    }

    @Test
    fun `execute onEvent for OnFetchWeatherData with success on calling the usecase`() = runTest {
        // Arrange
        val fakeModel = WeatherUiModel(
            weatherType = "Clouds",
            21,
            19,
            24,
            "Cluj-Napoca"
        )
        viewModel.locationState.value = Pair(1.0, 1.0)
        coEvery { mockGetWeatherUseCase.execute(any(), any()) } returns fakeModel
        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchWeatherData)

        // Assert
        coVerify { mockGetWeatherUseCase.execute(any(), any()) }
        assertEquals(fakeModel, viewModel.weatherState.value)
    }

    @Test
    fun `execute onEvent for OnFetchWeatherData with failure on calling the usecase`() = runTest {
        // Arrange
        viewModel.locationState.value = Pair(1.0, 1.0)
        coEvery { mockGetWeatherUseCase.execute(any(), any()) } returns null
        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchWeatherData)

        // Assert
        coVerify { mockGetWeatherUseCase.execute(any(), any()) }
        assertNull(viewModel.weatherState.value)
    }

    @Test
    fun `execute onEvent for OnFetchWeatherData with location null`() = runTest {
        // Arrange
        viewModel.locationState.value = null
        coEvery { mockGetWeatherUseCase.execute(any(), any()) } returns mockk()
        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchWeatherData)

        // Assert
        coVerify(exactly = 0) { mockGetWeatherUseCase.execute(any(), any()) }
        assertNull(viewModel.weatherState.value)
    }

    @Test
    fun `execute onEvent for OnFetchForecastData with success on calling the usecase`() = runTest {
        // Arrange
        val fakeModel = ForecastUiModel(
            forecast = listOf(
                ForecastDay(
                    "Monday",
                    "Clear",
                    22
                ),
                ForecastDay(
                    "Tuesday",
                    "Clear",
                    22
                ),
                ForecastDay(
                    "Wednesday",
                    "Clear",
                    22
                ),
                ForecastDay(
                    "Thursday",
                    "Clear",
                    22
                ),
                ForecastDay(
                    "Friday",
                    "Clear",
                    22
                )
            ),
        )

        viewModel.locationState.value = Pair(1.0, 1.0)
        coEvery { mockGetForecastUseCase.execute(any(), any()) } returns fakeModel
        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchForecastData)

        // Assert
        coVerify { mockGetForecastUseCase.execute(any(), any()) }
        assertEquals(fakeModel, viewModel.forecastState.value)
    }

    @Test
    fun `execute onEvent for OnFetchForecastData with failure on calling the usecase`() = runTest {
        // Arrange
        viewModel.locationState.value = Pair(1.0, 1.0)
        coEvery { mockGetForecastUseCase.execute(any(), any()) } returns null
        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchForecastData)

        // Assert
        coVerify { mockGetForecastUseCase.execute(any(), any()) }
        assertNull(viewModel.forecastState.value)
    }

    @Test
    fun `execute onEvent for OnFetchForecastData with location null`() = runTest {
        // Arrange
        viewModel.locationState.value = null
        coEvery { mockGetForecastUseCase.execute(any(), any()) } returns mockk()
        every { mockDataStoreRepository.getTimestamp() } returns flowOf("testTimestamp")

        // Act
        viewModel.onEvent(MainViewModel.Event.OnFetchForecastData)

        // Assert
        coVerify(exactly = 0) { mockGetForecastUseCase.execute(any(), any()) }
        assertNull(viewModel.forecastState.value)
    }

    @Test
    fun `execute onEvent for OnNavigateToNearbyScreen with location active`() = runTest {
        // Arrange
        viewModel.locationState.value = Pair(1.0, 1.0)
        val fakeModel = NearbyRestaurantsUiModel(
            listOf(
                Restaurant(
                    "Test shop",
                    4.55,
                    5332,
                    vicinity = "Test street"
                )
            )
        )
        coEvery { mockGetNearbyRestaurantsUseCase.execute(any()) } returns fakeModel

        // Act
        viewModel.onEvent(MainViewModel.Event.OnNavigateToNearbyScreen)

        // Assert
        coVerify { mockGetNearbyRestaurantsUseCase.execute(any()) }
        assertEquals(fakeModel, viewModel.nearbyRestaurantsState.value)
    }
}
