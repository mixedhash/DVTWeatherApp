package com.silosoft.technologies.dvtweatherapp.domain.ui_model

data class WeatherUiModel(
    val weatherType: String,
    val currentTemp: Int,
    val minTemp: Int,
    val maxTemp: Int,
    val locationName: String
)
