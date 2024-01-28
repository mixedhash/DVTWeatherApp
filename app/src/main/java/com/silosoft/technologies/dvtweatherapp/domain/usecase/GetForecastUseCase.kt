package com.silosoft.technologies.dvtweatherapp.domain.usecase

import android.content.Context
import android.widget.Toast
import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastDay
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val openWeatherRepository: OpenWeatherRepository
) {
    suspend fun execute(lat: String, lon: String): ForecastUiModel? {
        val result = openWeatherRepository.getForecast(lat, lon)
        val forecastDays = mutableListOf<ForecastDay>()
        return when (result) {
            is Result.Success -> {
                result.data.list.forEach { day ->
                    forecastDays.add(
                        ForecastDay(
                            dayOfWeek = convertToDayOfWeek(day.dtTxt),
                            weatherType = day.weather[0].main,
                            temp = day.main.temp.toInt()
                        )
                    )
                }
                ForecastUiModel(forecast = forecastDays)
            }
            is Result.Error -> {
                Timber.e(result.error)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
                }
                null
            }
        }
    }

    private fun convertToDayOfWeek(timestamp: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = formatter.parse(timestamp)
        val dayOfWeekFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return date?.let { dayOfWeekFormatter.format(it) } ?: ""
    }
}
