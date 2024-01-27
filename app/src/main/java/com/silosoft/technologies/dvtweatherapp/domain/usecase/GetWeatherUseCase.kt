package com.silosoft.technologies.dvtweatherapp.domain.usecase

import android.content.Context
import android.widget.Toast
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.Coordinates
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.WeatherUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val openWeatherRepository: OpenWeatherRepository
) {
    suspend fun execute(lat: String, lon: String): WeatherUiModel? {
        val result = openWeatherRepository.getWeather(lat, lon)
        return if (result == null) {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            null
        } else {
            WeatherUiModel(
                coordinates = Coordinates(lat = lat.toDouble(), lon = lon.toDouble()),
                weatherType = result.weather[0].main,
                currentTemp = result.main.temp,
                minTemp = result.main.tempMin,
                maxTemp = result.main.tempMax,
                locationName = result.name
            )
        }
    }
}
