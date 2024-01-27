package com.silosoft.technologies.dvtweatherapp.domain.usecase

import android.content.Context
import android.widget.Toast
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.ForecastDay
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.ForecastUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
        if (result == null) {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_LONG).show()
            return null
        } else {
            result.list.forEach { day ->
                forecastDays.add(
                    ForecastDay(
                        dayOfWeek = convertToDayOfWeek(day.dtTxt),
                        weatherType = day.weather[0].main,
                        temp = day.main.temp
                    )
                )
            }
        }

        return ForecastUiModel(forecast = forecastDays)
    }

    private fun convertToDayOfWeek(timestamp: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = formatter.parse(timestamp)
        val dayOfWeekFormatter = SimpleDateFormat("EEEE", Locale.getDefault())
        return date?.let { dayOfWeekFormatter.format(it) } ?: ""
    }
}
