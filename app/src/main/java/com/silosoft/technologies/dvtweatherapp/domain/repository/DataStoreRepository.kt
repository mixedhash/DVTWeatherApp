package com.silosoft.technologies.dvtweatherapp.domain.repository

import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    fun getTimestamp(): Flow<String>
    suspend fun storeWeather(weatherResponse: WeatherResponse)
    fun getWeather(): Flow<WeatherResponse?>
    suspend fun storeForecast(forecastResponse: ForecastResponse)
    fun getForecast(): Flow<ForecastResponse?>
}
