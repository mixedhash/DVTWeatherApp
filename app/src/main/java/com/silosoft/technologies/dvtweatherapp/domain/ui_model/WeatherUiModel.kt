package com.silosoft.technologies.dvtweatherapp.domain.ui_model

data class WeatherUiModel(
    val coordinates: Coordinates,
    val weatherType: String,
    val currentTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val locationName: String
)

data class Coordinates (
    val lat: Double,
    val lon: Double
)
