package com.silosoft.technologies.dvtweatherapp.domain.usecase

import com.silosoft.technologies.dvtweatherapp.data.Result
import com.silosoft.technologies.dvtweatherapp.domain.repository.OpenWeatherRepository
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastDay
import com.silosoft.technologies.dvtweatherapp.domain.model.ForecastUiModel
import com.silosoft.technologies.dvtweatherapp.extensions.convertToDayOfWeek
import timber.log.Timber
import javax.inject.Inject

class GetForecastUseCase @Inject constructor(
    private val openWeatherRepository: OpenWeatherRepository,
) {
    suspend fun execute(lat: String, lon: String): ForecastUiModel? {
        val result = openWeatherRepository.getForecast(lat, lon)
        val forecastDays = mutableListOf<ForecastDay>()
        return when (result) {
            // This will aggregate the 3 hours steps forecast into a daily forecast
            // with an average temperature and most common weather type found for that day
            is Result.Success -> {
                val groupedBySameDay = result.data.list.groupBy {
                    it.dtTxt.convertToDayOfWeek()
                }
                groupedBySameDay.forEach { (dayOfWeek, threeHourDailyForecast) ->
                    val averageTemperature =
                        threeHourDailyForecast.map { it.main.temp.toInt() }.average().toInt()

                    val weatherTypeCounter = HashMap<String, Int>()
                    threeHourDailyForecast.forEach {
                        weatherTypeCounter[it.weather[0].main] =
                            weatherTypeCounter.getOrDefault(it.weather[0].main, 0) + 1
                    }

                    // Selects most common weather type, if all of them are unique, select the first one
                    val mostCommonWeatherType = weatherTypeCounter.maxByOrNull { it.value }?.key
                        ?: threeHourDailyForecast.first().weather[0].main

                    forecastDays.add(
                        ForecastDay(
                            dayOfWeek = dayOfWeek,
                            weatherType = mostCommonWeatherType,
                            temp = averageTemperature
                        )
                    )
                }
                ForecastUiModel(forecast = forecastDays)
            }
            is Result.Error -> {
                Timber.e(result.error)
                null
            }
        }
    }
}
