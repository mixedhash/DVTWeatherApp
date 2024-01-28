package com.silosoft.technologies.dvtweatherapp

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.silosoft.technologies.dvtweatherapp.domain.repository.LocationRepository
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastUiModel
import com.silosoft.technologies.dvtweatherapp.domain.model.WeatherUiModel
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetForecastUseCase
import com.silosoft.technologies.dvtweatherapp.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val weatherUseCase: GetWeatherUseCase,
    private val forecastUseCase: GetForecastUseCase
) : ViewModel() {

    val locationState: MutableStateFlow<Location?> = MutableStateFlow(null)
    val weatherState: MutableStateFlow<WeatherUiModel?> = MutableStateFlow(null)
    val forecastState: MutableStateFlow<ForecastUiModel?> = MutableStateFlow(null)

    fun getLocation() = viewModelScope.launch(Dispatchers.IO) {
        locationState.value = locationRepository.getCurrentLocation()
    }

    fun getWeather() = locationState.value?.let { location ->
        viewModelScope.launch(Dispatchers.IO) {
            weatherState.value = weatherUseCase.execute(
                lat = location.latitude.toString(),
                lon = location.longitude.toString()
            )
        }
    }

    fun getForecast() = locationState.value?.let { location ->
        viewModelScope.launch(Dispatchers.IO) {
            forecastState.value = forecastUseCase.execute(
                lat = location.latitude.toString(),
                lon = location.longitude.toString()
            )
        }
    }
}
