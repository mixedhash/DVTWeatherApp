package com.silosoft.technologies.dvtweatherapp.domain.api

import com.silosoft.technologies.dvtweatherapp.BuildConfig
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET(BuildConfig.WEATHER_ENDPOINT_BASE_URL)
    suspend fun getWeather(
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @GET(BuildConfig.FORECAST_ENDPOINT_BASE_URL)
    suspend fun getForecast(
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric"
    ): ForecastResponse
}
