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
            is Result.Success -> {
                result.data.list.forEach { day ->
                    forecastDays.add(
                        ForecastDay(
                            dayOfWeek = day.dtTxt.convertToDayOfWeek(),
                            weatherType = day.weather[0].main,
                            temp = day.main.temp.toInt()
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
