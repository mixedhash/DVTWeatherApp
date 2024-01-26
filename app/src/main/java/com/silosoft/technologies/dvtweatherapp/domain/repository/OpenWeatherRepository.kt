package com.silosoft.technologies.dvtweatherapp.domain.repository

import com.silosoft.technologies.dvtweatherapp.data.response_model.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response_model.weather.WeatherResponse

interface OpenWeatherRepository {
    suspend fun getWeather(lat: String, lon: String): WeatherResponse?
    suspend fun getForecast(lat: String, lon: String): ForecastResponse?
}
