package com.silosoft.technologies.dvtweatherapp.data.response_model.forecast

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Wind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)
