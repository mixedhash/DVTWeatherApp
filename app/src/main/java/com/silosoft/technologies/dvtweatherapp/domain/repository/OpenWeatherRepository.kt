package com.silosoft.technologies.dvtweatherapp.domain.repository

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse

interface OpenWeatherRepository {
    suspend fun getWeather(lat: String, lon: String): Result<WeatherResponse>
    suspend fun getForecast(lat: String, lon: String): Result<ForecastResponse>
}
