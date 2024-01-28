package com.silosoft.technologies.dvtweatherapp.data.implementations

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response_model.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response_model.weather.WeatherResponse
import com.silosoft.technologies.dvtweatherapp.domain.api.OpenWeatherApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : OpenWeatherRepository {
    override suspend fun getWeather(lat: String, lon: String): Result<WeatherResponse> =
        try {
            val response = openWeatherApi.getWeather(lat = lat, lon = lon)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(IOException("Response body is null"))
            } else {
                Result.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

    override suspend fun getForecast(lat: String, lon: String): Result<ForecastResponse> =
        try {
            val response = openWeatherApi.getForecast(lat = lat, lon = lon)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.Success(it)
                } ?: Result.Error(IOException("Response body is null"))
            } else {
                Result.Error(HttpException(response))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }

}
