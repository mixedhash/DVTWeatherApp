package com.silosoft.technologies.dvtweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.NearbyRestaurantsUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.WeatherUiModel
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetForecastUseCase
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetNearbyRestaurantsUseCase
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherUseCase: GetWeatherUseCase,
    private val forecastUseCase: GetForecastUseCase,
    private val nearbyRestaurantsUseCase: GetNearbyRestaurantsUseCase,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            dataStoreRepository.getTimestamp().collectLatest {
                timestampState.value = it
            }
        }
    }

    val locationState: MutableStateFlow<Pair<Double, Double>?> = MutableStateFlow(null)
    val weatherState: MutableStateFlow<WeatherUiModel?> = MutableStateFlow(null)
    val forecastState: MutableStateFlow<ForecastUiModel?> = MutableStateFlow(null)
    val displayErrorToast: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val timestampState: MutableStateFlow<String?> = MutableStateFlow(null)
    val nearbyRestaurantsState: MutableStateFlow<NearbyRestaurantsUiModel?> = MutableStateFlow(null)

    private fun getLocation() = viewModelScope.launch(Dispatchers.IO) {
        locationState.value = locationRepository.getCurrentLocation()
    }

    private fun getWeather() = locationState.value?.let { location ->
        viewModelScope.launch(Dispatchers.IO) {
            weatherState.value = weatherUseCase.execute(
                lat = location.first.toString(),
                lon = location.second.toString()
            )
        }
    }

    private fun getForecast() = locationState.value?.let { location ->
        viewModelScope.launch(Dispatchers.IO) {
            forecastState.value = forecastUseCase.execute(
                lat = location.first.toString(),
                lon = location.second.toString()
            )
        }
    }

    private fun getNearbyRestaurants() = locationState.value?.let { location ->
        viewModelScope.launch(Dispatchers.IO) {
            nearbyRestaurantsState.value = nearbyRestaurantsUseCase.execute(
                location = "${location.first},${location.second}"
            )
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnFetchLocation -> getLocation()
            is Event.OnFetchWeatherData -> getWeather()
            is Event.OnFetchForecastData -> getForecast()
            is Event.OnDisplayErrorToast -> displayErrorToast.value = true
            is Event.OnDisplayErrorToastFinished -> displayErrorToast.value = false
            is Event.OnNavigateToNearbyScreen -> getNearbyRestaurants()
        }
    }

    sealed interface Event {
        data object OnFetchLocation : Event
        data object OnFetchWeatherData : Event
        data object OnFetchForecastData : Event
        data object OnDisplayErrorToast : Event
        data object OnDisplayErrorToastFinished : Event
        data object OnNavigateToNearbyScreen : Event
    }
}
