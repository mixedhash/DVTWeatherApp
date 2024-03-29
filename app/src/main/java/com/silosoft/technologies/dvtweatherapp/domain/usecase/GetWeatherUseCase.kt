package com.silosoft.technologies.dvtweatherapp.domain.usecase

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.model.WeatherUiModel
import timber.log.Timber
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository
) {
    suspend fun execute(lat: String, lon: String): WeatherUiModel? {
        return when (val result = openWeatherRepository.getWeather(lat, lon)) {
            is Result.Success -> {
                WeatherUiModel(
                    weatherType = result.data.weather[0].main,
                    currentTemp = result.data.main.temp.toInt(),
                    minTemp = result.data.main.tempMin.toInt(),
                    maxTemp = result.data.main.tempMax.toInt(),
                    locationName = result.data.name
                )
            }
            is Result.Error -> {
                Timber.e(result.error)
                null
            }
        }
    }
}
