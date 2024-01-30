package com.silosoft.technologies.dvtweatherapp.data.implementations

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.silosoft.technologies.dvtweatherapp.data.response.forecast.ForecastResponse
import com.silosoft.technologies.dvtweatherapp.data.response.weather.WeatherResponse
import com.silosoft.technologies.dvtweatherapp.domain.repository.DataStoreRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TIMESTAMP_PREFERENCES_KEY = "timestamp"
private const val WEATHER_PREFERENCES_KEY = "weather"
private const val FORECAST_PREFERENCES_KEY = "forecast"

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    moshi: Moshi
) : DataStoreRepository {

    private val weatherAdapter = moshi.adapter(WeatherResponse::class.java)
    private val forecastAdapter = moshi.adapter(ForecastResponse::class.java)

    override fun getTimestamp(): Flow<String> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(TIMESTAMP_PREFERENCES_KEY)] ?: "No timestamp available"
        }

    override suspend fun storeWeather(weatherResponse: WeatherResponse) {
        dataStore.edit { preferences ->
            try {
                preferences[stringPreferencesKey(WEATHER_PREFERENCES_KEY)] =
                    weatherAdapter.toJson(weatherResponse)
                preferences[stringPreferencesKey(TIMESTAMP_PREFERENCES_KEY)] = generateTimestamp()

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    override fun getWeather(): Flow<WeatherResponse?> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(WEATHER_PREFERENCES_KEY)]?.let { weatherJson ->
                weatherAdapter.fromJson(weatherJson)
            }
        }

    override suspend fun storeForecast(forecastResponse: ForecastResponse) {
        dataStore.edit { preferences ->
            preferences[stringPreferencesKey(FORECAST_PREFERENCES_KEY)] =
                forecastAdapter.toJson(forecastResponse)
            preferences[stringPreferencesKey(TIMESTAMP_PREFERENCES_KEY)] = generateTimestamp()
        }
    }

    override fun getForecast(): Flow<ForecastResponse?> =
        dataStore.data.map { preferences ->
            preferences[stringPreferencesKey(FORECAST_PREFERENCES_KEY)]?.let { forecastJson ->
                forecastAdapter.fromJson(forecastJson)
            }
        }

    private fun generateTimestamp(): String {
        val s = SimpleDateFormat("hh:mm dd MMMM yyyy", Locale.getDefault())
        return s.format(Date())
    }
}
