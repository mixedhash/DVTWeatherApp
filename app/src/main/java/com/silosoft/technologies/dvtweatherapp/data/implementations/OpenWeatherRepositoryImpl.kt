package com.silosoft.technologies.dvtweatherapp.data.implementations

import com.silosoft.technologies.dvtweatherapp.data.Result.Error
import com.silosoft.technologies.dvtweatherapp.data.Result.Success
import com.silosoft.technologies.dvtweatherapp.data.response_model.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response_model.weather.WeatherResponse
import com.silosoft.technologies.dvtweatherapp.domain.api.OpenWeatherApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import timber.log.Timber
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
): OpenWeatherRepository {
    override suspend fun getWeather(lat: String, lon: String): WeatherResponse? {
        return try {
            when (val response = openWeatherApi.getWeather(lat = lat, lon = lon)) {
                is Success -> response.data
                is Error -> throw Exception(response.error)
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    override suspend fun getForecast(lat: String, lon: String): ForecastResponse? {
        return try {
            when (val response = openWeatherApi.getForecast(lat = lat, lon = lon)) {
                is Success -> response.data
                is Error -> throw Exception(response.error)
            }
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }
}
