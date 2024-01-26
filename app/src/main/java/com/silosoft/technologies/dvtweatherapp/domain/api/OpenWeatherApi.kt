package com.silosoft.technologies.dvtweatherapp.domain.api

import com.silosoft.technologies.dvtweatherapp.BuildConfig
import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response_model.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response_model.weather.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
    ): Result<WeatherResponse>

    @GET("forecast")
    suspend fun getForecast(
        @Query("appid") appId: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String = "metric",
    ): Result<ForecastResponse>
}
