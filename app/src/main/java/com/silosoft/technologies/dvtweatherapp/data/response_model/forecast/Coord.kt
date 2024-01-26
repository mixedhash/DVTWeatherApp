package com.silosoft.technologies.dvtweatherapp.data.response_model.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Coord(
    val lat: Double,
    val lon: Double
)
