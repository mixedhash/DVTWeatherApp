package com.silosoft.technologies.dvtweatherapp.domain.ui_model

data class ForecastUiModel(
    val forecast: List<ForecastDay>
)

data class ForecastDay(
    val dayOfWeek: String,
    val weatherType: String,
    val temp: Double
)
