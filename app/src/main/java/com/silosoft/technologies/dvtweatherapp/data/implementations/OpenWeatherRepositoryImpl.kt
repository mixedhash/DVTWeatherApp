package com.silosoft.technologies.dvtweatherapp.data.implementations

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse
import com.silosoft.technologies.dvtweatherapp.domain.api.OpenWeatherApi
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import kotlinx.coroutines.flow.firstOrNull
import okio.IOException
import timber.log.Timber
import javax.inject.Inject

class OpenWeatherRepositoryImpl @Inject constructor(
    private val openWeatherApi: OpenWeatherApi,
    private val dataStoreRepository: DataStoreRepository
) : OpenWeatherRepository {

    // Not ideal, normally you should catch whatever is thrown by Retrofit + Datastore here
    @Suppress("SwallowedException")
    override suspend fun getWeather(lat: String, lon: String): Result<WeatherResponse> =
        try {
            val response = openWeatherApi.getWeather(lat = lat, lon = lon)
            if (response.isSuccessful) {
                response.body()?.let { responseData ->
                    dataStoreRepository.storeWeather(responseData)
                    Result.Success(responseData)
                } ?: Result.Error(IOException("Response body is null"))
            } else {
                fetchWeatherFromDataStore()
            }
        } catch (e: Exception) {
            Timber.e(e)
            fetchWeatherFromDataStore()
        }

    // Not ideal, normally you should catch whatever is thrown by Retrofit + Datastore here
    @Suppress("SwallowedException")
    override suspend fun getForecast(lat: String, lon: String): Result<ForecastResponse> =
        try {
            val response = openWeatherApi.getForecast(lat = lat, lon = lon)
            if (response.isSuccessful) {
                response.body()?.let { responseData ->
                    dataStoreRepository.storeForecast(responseData)
                    Result.Success(responseData)
                } ?: Result.Error(IOException("Response body is null"))
            } else {
                fetchForecastFromDataStore()
            }
        } catch (e: Exception) {
            Timber.e(e)
            fetchForecastFromDataStore()
        }

    private suspend fun fetchWeatherFromDataStore(): Result<WeatherResponse> {
        val lastWeatherResponse = dataStoreRepository.getWeather().firstOrNull()

        return if (lastWeatherResponse != null) {
            Result.Success(lastWeatherResponse)
        } else {
            Timber.e("No weather response yet in data store!")
            Result.Error(IOException("No data available locally"))
        }
    }

    private suspend fun fetchForecastFromDataStore(): Result<ForecastResponse> {
        val lastForecastResponse = dataStoreRepository.getForecast().firstOrNull()

        return if (lastForecastResponse != null) {
            Result.Success(lastForecastResponse)
        } else {
            Timber.e("No forecast response yet in data store!")
            Result.Error(IOException("No data available locally"))
        }
    }
}
