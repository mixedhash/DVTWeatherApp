package com.silosoft.technologies.dvtweatherapp.data.response_model.weather

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    val lat: Double,
    val lon: Double
)
